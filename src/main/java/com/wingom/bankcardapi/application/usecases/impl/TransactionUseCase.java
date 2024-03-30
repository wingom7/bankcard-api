package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.TransactionOutputDTO;
import com.wingom.bankcardapi.application.usecases.ITransactionUseCase;
import com.wingom.bankcardapi.domain.entities.Card;
import com.wingom.bankcardapi.domain.entities.Transaction;
import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
import com.wingom.bankcardapi.domain.enums.CurrencyUnitEnum;
import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import com.wingom.bankcardapi.domain.exceptions.IllegalStateException;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.exceptions.IllegalParamException;
import com.wingom.bankcardapi.domain.exceptions.IllegalCurrencyException;
import com.wingom.bankcardapi.domain.exceptions.InsufficientFundsException;
import com.wingom.bankcardapi.domain.repositories.CardRepository;
import com.wingom.bankcardapi.domain.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class TransactionUseCase implements ITransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final SecureRandom random = new SecureRandom();

    private static final String CURRENT_CURRENCY_UNIT = "USD";

    @Override
    public TransactionOutputDTO createPurchase(String cardId, BigDecimal amount) {

        Card card = cardRepository.findByCardNumber(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with number: " + cardId));

        MonetaryAmount monetaryAmount = Money.of(amount, CurrencyUnitEnum.USD.toString());

        if (!CURRENT_CURRENCY_UNIT.equals(monetaryAmount.getCurrency().getCurrencyCode())) {
            throw new IllegalCurrencyException("Currency must be USD");
        }

        if (card.getStatus() != CardStatusEnum.ACTIVE) {
            throw new IllegalStateException("Transaction cannot be completed: Card is not active.");
        }

        if (card.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for the transaction.");
        }

        String transactionNumber = generateUniqueTransactionNumber();

        card.setBalance(card.getBalance().subtract(amount));
        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionNumber);
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatusEnum.COMPLETED);

        Transaction savedTransaction = transactionRepository.save(transaction);
        TransactionOutputDTO transactionOutputDTO = modelMapper.map(savedTransaction, TransactionOutputDTO.class);
        transactionOutputDTO.setTransactionNumber(transaction.getTransactionNumber());
        transactionOutputDTO.setCardNumber(transaction.getCard().getCardNumber());
        return transactionOutputDTO;
    }

    @Override
    public TransactionOutputDTO getTransaction(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionNumber(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + transactionId));
        TransactionOutputDTO transactionOutputDTO = modelMapper.map(transaction, TransactionOutputDTO.class);
        transactionOutputDTO.setTransactionNumber(transaction.getTransactionNumber());
        transactionOutputDTO.setCardNumber(transaction.getCard().getCardNumber());
        return transactionOutputDTO;
    }

    @Override
    public TransactionOutputDTO annulTransaction(String transactionId, String cardId) {
        Transaction transaction = transactionRepository.findByTransactionNumber(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + transactionId));

        if (!transaction.getCard().getCardNumber().equals(cardId)) {
            throw new IllegalParamException("Card ID does not match the transaction record.");
        }

        if (transaction.getStatus() == TransactionStatusEnum.ANNULLED) {
            throw new IllegalStateException("Transaction is already annulled.");
        }

        if (transaction.getTransactionDate().isBefore(LocalDateTime.now().minusHours(24))) {
            throw new IllegalStateException("Transaction cannot be cancelled after 24 hours.");
        }

        Card card = transaction.getCard();
        card.setBalance(card.getBalance().add(transaction.getAmount()));
        cardRepository.save(card);

        transaction.setStatus(TransactionStatusEnum.ANNULLED);
        transaction = transactionRepository.save(transaction);

        TransactionOutputDTO transactionOutputDTO = modelMapper.map(transaction, TransactionOutputDTO.class);
        transactionOutputDTO.setTransactionNumber(transaction.getTransactionNumber());
        transactionOutputDTO.setCardNumber(transaction.getCard().getCardNumber());
        return transactionOutputDTO;
    }

    private String generateUniqueTransactionNumber() {
        Integer num;
        String uniqueCode;
        do {
            num = 100000 + random.nextInt(900000);
            uniqueCode = String.valueOf(num);
        } while (transactionRepository.existsByTransactionNumber(uniqueCode));

        return uniqueCode;
    }

}
