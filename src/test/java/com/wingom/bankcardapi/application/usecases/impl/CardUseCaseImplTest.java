package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.CardBalanceOutputDTO;
import com.wingom.bankcardapi.application.dto.CardDTO;
import com.wingom.bankcardapi.application.dto.CardDTOTest;
import com.wingom.bankcardapi.application.dto.CardNumberOutputDTO;
import com.wingom.bankcardapi.domain.entities.Card;
import com.wingom.bankcardapi.domain.entities.Product;
import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.repositories.CardRepository;
import com.wingom.bankcardapi.domain.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;

public class CardUseCaseImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CardUseCase cardUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGenerateCardNumberWithValidProductThenSuccess() {
        Long productCode = 1L;
        Product product = new Product();
        product.setProductCode("570994");
        when(productRepository.findByProductCode(String.valueOf(productCode)))
                .thenReturn(Optional.of(product));
        when(cardRepository.existsByCardNumber(anyString())).thenReturn(false);

        CardNumberOutputDTO result = cardUseCase.generateCardNumber(productCode);

        verify(cardRepository, times(1)).save(any(Card.class));
        assertEquals(16, result.getCardNumber().length());
    }

    @Test
    public void whenGenerateCardNumberWithInvalidProductThenThrowEntityNotFoundException() {
        Long productCode = 2L;
        when(productRepository.findByProductCode(String.valueOf(productCode)))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardUseCase.generateCardNumber(productCode));
    }

    @Test
    public void whenActivateCardWithInactiveCardThenCardActivated() {
        String cardNumber = "5234567890896543";
        Card card = new Card();
        card.setStatus(CardStatusEnum.INACTIVE);
        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Card.class), eq(CardDTOTest.class))).thenReturn(new CardDTOTest());

        CardDTO result = cardUseCase.activateCard(cardNumber);

        verify(cardRepository, times(1)).save(card);
        assertEquals(CardStatusEnum.ACTIVE, card.getStatus());
    }

    @Test
    public void whenActivateCardWithActiveCardThenStatusUnchanged() {
        String cardNumber = "5634567882896542";
        Card card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);
        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));
        when(modelMapper.map(any(Card.class), eq(CardDTOTest.class))).thenReturn(new CardDTOTest());

        CardDTO result = cardUseCase.activateCard(cardNumber);

        verify(cardRepository, never()).save(card);
    }

    @Test
    public void whenActivateCardWithNonExistentCardThenThrowEntityNotFoundException() {
        String cardNumber = "nonexistent";
        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cardUseCase.activateCard(cardNumber));
    }

    @Test
    public void whenBlockCardThenCardBlocked() {
        String cardNumber = "5834567890865432";
        Card card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);
        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Card.class), eq(CardDTOTest.class))).thenReturn(new CardDTOTest());

        CardDTO result = cardUseCase.blockCard(cardNumber);

        verify(cardRepository, times(1)).save(card);
        assertEquals(CardStatusEnum.BLOCKED, card.getStatus(), "The card must be in BLOCKED status");
    }

    @Test
    public void whenBlockCardWithNonExistentCardThenThrowEntityNotFoundException() {
        String cardNumber = "nonexistent";
        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardUseCase.blockCard(cardNumber), "must launch EntityNotFoundException for cards not found");
    }

    @Test
    public void whenRechargeCardThenBalanceUpdated() {
        String cardId = "6234567890865432";
        Card card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);
        card.setBalance(new BigDecimal("100"));
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Card.class), eq(CardDTOTest.class))).thenReturn(new CardDTOTest());

        CardDTO result = cardUseCase.rechargeCard(cardId, new BigDecimal("50"));

        verify(cardRepository, times(1)).save(card);
        assertEquals(0, card.getBalance().compareTo(new BigDecimal("150")), "The card balance must be updated correctly.");
    }

    @Test
    public void whenRechargeBlockedCardThenThrowIllegalStateException() {
        String cardId = "blockedCard";
        Card card = new Card();
        card.setStatus(CardStatusEnum.BLOCKED);
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class, () -> cardUseCase.rechargeCard(cardId, new BigDecimal("50")), "must launch IllegalStateException for blocked cards.");
    }

    @Test
    public void whenRechargeInactiveCardThenThrowIllegalStateException() {
        String cardId = "inactiveCard";
        Card card = new Card();
        card.setStatus(CardStatusEnum.INACTIVE);
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class, () -> cardUseCase.rechargeCard(cardId, new BigDecimal("50")), "must launch IllegalStateException for inactive cards.");
    }

    @Test
    public void whenRechargeNonExistentCardThenThrowEntityNotFoundException() {
        String cardId = "nonexistent";
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardUseCase.rechargeCard(cardId, new BigDecimal("50")), "must launch EntityNotFoundException for cards not found.");
    }

    @Test
    public void whenGetCardBalanceThenSuccess() {
        String cardId = "523456789865431";
        BigDecimal expectedBalance = new BigDecimal("100.00");
        Card card = new Card();
        card.setBalance(expectedBalance);
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.of(card));

        CardBalanceOutputDTO result = cardUseCase.getCardBalance(cardId);

        assertEquals(expectedBalance, result.getBalance(), "The returned balance must match the expected balance.");
    }

    @Test
    public void whenGetCardBalanceWithNonExistentCardThenThrowEntityNotFoundException() {
        String cardId = "nonexistent";
        when(cardRepository.findByCardNumber(cardId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardUseCase.getCardBalance(cardId), "must launch EntityNotFoundException for cards not found.");
    }
}
