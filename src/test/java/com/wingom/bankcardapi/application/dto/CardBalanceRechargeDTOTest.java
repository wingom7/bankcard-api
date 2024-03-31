package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardBalanceRechargeDTOTest {

    @Test
    public void testNoArgsConstructor() {
        CardBalanceRechargeDTO cardBalanceRechargeDTO = new CardBalanceRechargeDTO();
        assertNotNull(cardBalanceRechargeDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        CardBalanceRechargeDTO cardBalanceRechargeDTO = new CardBalanceRechargeDTO("6890987654567876", BigDecimal.ZERO);
        assertNotNull(cardBalanceRechargeDTO);
        assertEquals("6890987654567876", cardBalanceRechargeDTO.getCardId());
    }

    @Test
    public void testGettersAndSetters() {
        CardBalanceRechargeDTO cardBalanceRechargeDTO = new CardBalanceRechargeDTO();
        cardBalanceRechargeDTO.setCardId("6790987654345676");
        cardBalanceRechargeDTO.setBalance(BigDecimal.ZERO);
        assertEquals("6790987654345676", cardBalanceRechargeDTO.getCardId());
        assertEquals(BigDecimal.ZERO, cardBalanceRechargeDTO.getBalance());
    }

    @Test
    public void testEqualsAndHashCode() {
        CardBalanceRechargeDTO cardBalanceRechargeDTO1 = new CardBalanceRechargeDTO("6890987654567876", BigDecimal.ZERO);
        CardBalanceRechargeDTO cardBalanceRechargeDTO2 = new CardBalanceRechargeDTO("6890987654567876", BigDecimal.ZERO);
        assertEquals(cardBalanceRechargeDTO1, cardBalanceRechargeDTO2);
        assertEquals(cardBalanceRechargeDTO2.hashCode(), cardBalanceRechargeDTO2.hashCode());
    }

    @Test
    public void testToString() {
        CardBalanceRechargeDTO cardBalanceRechargeDTO = new CardBalanceRechargeDTO("5678908765432456", BigDecimal.ZERO);
        String cardString = cardBalanceRechargeDTO.toString();
        assertTrue(cardString.contains("5678908765432456"));
    }
}
