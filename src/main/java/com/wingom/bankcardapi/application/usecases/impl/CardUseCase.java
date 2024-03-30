package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.CardBalanceOutputDTO;
import com.wingom.bankcardapi.application.dto.CardDTO;
import com.wingom.bankcardapi.application.dto.CardNumberOutputDTO;
import com.wingom.bankcardapi.application.usecases.ICardUseCase;
import com.wingom.bankcardapi.domain.entities.Card;
import com.wingom.bankcardapi.domain.entities.Product;
import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.repositories.CardRepository;
import com.wingom.bankcardapi.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class CardUseCase implements ICardUseCase {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final SecureRandom random = new SecureRandom();

    @Override
    public CardNumberOutputDTO generateCardNumber(Long productCode) {

        Product product = productRepository.findByProductCode(String.valueOf(productCode))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with code: " + productCode));

        String cardNumber;
        do {
            cardNumber = product.getProductCode() + generateRandomDigits();
        } while (cardRepository.existsByCardNumber(cardNumber));

        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setProduct(product);
        card.setBalance(new BigDecimal("0.0"));
        card.setStatus(CardStatusEnum.INACTIVE);
        card.setExpirationDate(LocalDate.now().plusYears(3));
        cardRepository.save(card);

        return new CardNumberOutputDTO(cardNumber);
    }

    @Override
    public CardDTO activateCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with number: " + cardNumber));

        if (card.getStatus() != CardStatusEnum.ACTIVE) {
            card.setStatus(CardStatusEnum.ACTIVE);
            card = cardRepository.save(card);
        }

        return modelMapper.map(card, CardDTO.class);
    }

    @Override
    public CardDTO blockCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with number: " + cardNumber));
        card.setStatus(CardStatusEnum.BLOCKED);
        card = cardRepository.save(card);
        return modelMapper.map(card, CardDTO.class);
    }


    @Override
    public CardDTO rechargeCard(String cardId, BigDecimal balance) {
        Card card = cardRepository.findByCardNumber(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id: " + cardId));

        if (card.getStatus() == CardStatusEnum.BLOCKED) {
            throw new IllegalStateException("Cannot recharge a blocked card.");
        }

        if (card.getStatus() == CardStatusEnum.INACTIVE) {
            throw new IllegalStateException("Cannot recharge an inactive card.");
        }

        BigDecimal newBalance = card.getBalance().add(balance);
        card.setBalance(newBalance);
        cardRepository.save(card);

        return modelMapper.map(card, CardDTO.class);
    }

    @Override
    public CardBalanceOutputDTO getCardBalance(String cardId) {
        Card card = cardRepository.findByCardNumber(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with number: " + cardId));

        return new CardBalanceOutputDTO(card.getBalance());
    }

    private String generateRandomDigits() {
        return random.ints(10, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }
}
