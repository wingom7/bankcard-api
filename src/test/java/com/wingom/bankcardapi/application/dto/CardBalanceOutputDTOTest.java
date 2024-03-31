package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardBalanceOutputDTOTest {

    @Test
    public void testNoArgsConstructor() {
        CardBalanceOutputDTO cardBalanceOutputDTO = new CardBalanceOutputDTO();
        assertNotNull(cardBalanceOutputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        CardBalanceOutputDTO cardBalanceOutputDTO = new CardBalanceOutputDTO(BigDecimal.ZERO);
        assertNotNull(cardBalanceOutputDTO);
        assertEquals(BigDecimal.ZERO, cardBalanceOutputDTO.getBalance());
    }

    @Test
    public void testGettersAndSetters() {
        CardBalanceOutputDTO cardBalanceOutputDTO = new CardBalanceOutputDTO();
        cardBalanceOutputDTO.setBalance(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, cardBalanceOutputDTO.getBalance());
    }

    @Test
    public void testEqualsAndHashCode() {
        CardBalanceOutputDTO cardBalanceOutputDTO1 = new CardBalanceOutputDTO(BigDecimal.ZERO);
        CardBalanceOutputDTO cardBalanceOutputDTO2 = new CardBalanceOutputDTO(BigDecimal.ZERO);
        assertEquals(cardBalanceOutputDTO1, cardBalanceOutputDTO2);
        assertEquals(cardBalanceOutputDTO2.hashCode(), cardBalanceOutputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        CardBalanceOutputDTO cardBalanceOutputDTO = new CardBalanceOutputDTO(BigDecimal.ZERO);
        String cardString = cardBalanceOutputDTO.toString();
        assertTrue(cardString.contains(String.valueOf(BigDecimal.ZERO)));
    }
}
