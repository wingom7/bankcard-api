package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.TransactionOutputDTO;
import com.wingom.bankcardapi.domain.entities.Card;
import com.wingom.bankcardapi.domain.entities.Transaction;
import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.exceptions.IllegalParamException;
import com.wingom.bankcardapi.domain.exceptions.IllegalStateException;
import com.wingom.bankcardapi.domain.exceptions.InsufficientFundsException;
import com.wingom.bankcardapi.domain.repositories.CardRepository;
import com.wingom.bankcardapi.domain.repositories.TransactionRepository;

import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import org.mockito.MockitoAnnotations;

public class TransactionUseCaseImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionUseCase transactionUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreatePurchaseWithActiveCardAndSufficientFundsThenSuccess() {
        String cardId = "5234567890895434";
        Card card = new Card();
        card.setCardNumber(cardId);
        card.setStatus(CardStatusEnum.ACTIVE);
        card.setBalance(new BigDecimal("100.00"));

        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber("123456");
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setStatus(TransactionStatusEnum.COMPLETED);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(modelMapper.map(any(Transaction.class), eq(TransactionOutputDTO.class))).thenReturn(new TransactionOutputDTO());

        TransactionOutputDTO result = transactionUseCase.createPurchase(cardId, new BigDecimal("50.00"));

        assertNotNull(result);
        verify(cardRepository, times(1)).save(card);
        assertEquals(0, card.getBalance().compareTo(new BigDecimal("50.00")), "Card balance should be 50.00 after transaction.");
    }

    @Test
    public void whenCreatePurchaseWithInactiveCardThenThrowIllegalStateException() {
        String cardId = "inactiveCard";
        Card card = new Card();
        card.setStatus(CardStatusEnum.INACTIVE);

        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class, () -> transactionUseCase.createPurchase(cardId, new BigDecimal("50.00")), "Should throw IllegalStateException for inactive cards.");
    }

    @Test
    public void whenCreatePurchaseWithInsufficientFundsThenThrowInsufficientFundsException() {
        String cardId = "lowFundsCard";
        Card card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);
        card.setBalance(new BigDecimal("30.00"));

        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        assertThrows(InsufficientFundsException.class, () -> transactionUseCase.createPurchase(cardId, new BigDecimal("50.00")), "Should throw InsufficientFundsException for insufficient funds.");
    }

    @Test
    public void whenCreatePurchaseWithNonexistentCardThenThrowEntityNotFoundException() {
        String cardId = "nonexistentCard";

        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> transactionUseCase.createPurchase(cardId, new BigDecimal("50.00")),
                "Should throw EntityNotFoundException for cards not found.");
    }

    @Test
    public void whenGetTransactionWithValidIdThenSuccess() {
        String transactionId = "654342";
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionId);
        Card card = new Card();
        card.setCardNumber("6765435679265432");
        transaction.setCard(card);

        TransactionOutputDTO expectedDTO = new TransactionOutputDTO();
        expectedDTO.setTransactionNumber(transaction.getTransactionNumber());
        expectedDTO.setCardNumber(transaction.getCard().getCardNumber());

        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.of(transaction));
        when(modelMapper.map(transaction, TransactionOutputDTO.class)).thenReturn(expectedDTO);

        TransactionOutputDTO result = transactionUseCase.getTransaction(transactionId);

        assertNotNull(result);
        assertEquals(transactionId, result.getTransactionNumber(), "Transaction ID must match");
        assertEquals("6765435679265432", result.getCardNumber(), "Card number must match");
    }

    @Test
    public void whenGetTransactionWithNonexistentIdThenThrowEntityNotFoundException() {
        String transactionId = "nonexistentTrans";
        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionUseCase.getTransaction(transactionId), "Should throw EntityNotFoundException for non-existing transaction ID");
    }

    @Test
    public void whenAnnulTransactionWithValidConditionsThenSuccess() {
        String transactionId = "543456";
        String cardId = "6543245689908765";
        BigDecimal transactionAmount = new BigDecimal("100.00");
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionId);
        transaction.setStatus(TransactionStatusEnum.COMPLETED);
        transaction.setTransactionDate(LocalDateTime.now().minusHours(1));
        transaction.setAmount(transactionAmount);

        Card card = new Card();
        card.setCardNumber(cardId);
        card.setBalance(new BigDecimal("500.00"));
        transaction.setCard(card);

        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(modelMapper.map(any(Transaction.class), eq(TransactionOutputDTO.class))).thenReturn(new TransactionOutputDTO());

        TransactionOutputDTO result = transactionUseCase.annulTransaction(transactionId, cardId);

        assertNotNull(result);
        assertEquals(TransactionStatusEnum.ANNULLED, transaction.getStatus(), "Transaction status should be ANNULLED");
        verify(transactionRepository, times(1)).save(transaction);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void whenAnnulTransactionWithCardIdMismatchThenThrowIllegalParamException() {
        String transactionId = "897654";
        String cardId = "5436898654322345";
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionId);
        transaction.setStatus(TransactionStatusEnum.COMPLETED);

        Card card = new Card();
        card.setCardNumber("differentCardId");
        transaction.setCard(card);

        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.of(transaction));

        assertThrows(IllegalParamException.class, () -> transactionUseCase.annulTransaction(transactionId, cardId), "Should throw IllegalParamException for card ID mismatch.");
    }

    @Test
    public void whenAnnulTransactionAlreadyAnnulled_thenThrowIllegalStateException() {
        String transactionId = "654358";
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionId);
        transaction.setStatus(TransactionStatusEnum.ANNULLED);
        Card card = new Card();
        card.setCardNumber("5432678908765434");
        transaction.setCard(card);

        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.of(transaction));
        TransactionOutputDTO mockOutputDTO = new TransactionOutputDTO();
        when(modelMapper.map(any(Transaction.class), eq(TransactionOutputDTO.class))).thenReturn(mockOutputDTO);

        assertThrows(IllegalStateException.class,
                () -> transactionUseCase.annulTransaction(transactionId, "5432678908765434"),
                "Should throw IllegalStateException for already annulled transaction.");
    }


    @Test
    public void whenAnnulTransactionAfter24HoursThenThrowIllegalStateException() {
        String transactionId = "897654";
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(transactionId);
        transaction.setStatus(TransactionStatusEnum.COMPLETED);
        transaction.setTransactionDate(LocalDateTime.now().minusDays(2));
        Card card = new Card();
        card.setCardNumber("6546789087654324");
        transaction.setCard(card);

        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.of(transaction));

        TransactionOutputDTO mockOutputDTO = new TransactionOutputDTO();
        when(modelMapper.map(any(Transaction.class), eq(TransactionOutputDTO.class))).thenReturn(mockOutputDTO);

        assertThrows(IllegalStateException.class, () -> transactionUseCase.annulTransaction(transactionId, "6546789087654324"), "Should throw IllegalStateException for transactions older than 24 hours.");
    }


    @Test
    public void whenAnnulTransactionWithNonexistentIdThenThrowEntityNotFoundException() {
        String transactionId = "nonexistentTxn";
        when(transactionRepository.findByTransactionNumber(transactionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionUseCase.annulTransaction(transactionId, "card123"), "Should throw EntityNotFoundException for a nonexistent transaction.");
    }

}