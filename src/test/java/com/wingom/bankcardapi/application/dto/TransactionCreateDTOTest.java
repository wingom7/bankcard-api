package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionCreateDTOTest {
    @Test
    public void testNoArgsConstructor() {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO();
        assertNotNull(transactionCreateDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO(1L, BigDecimal.ZERO);
        assertNotNull(transactionCreateDTO);
        assertEquals(1L, transactionCreateDTO.getCardId());
    }

    @Test
    public void testGettersAndSetters() {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO();
        transactionCreateDTO.setCardId(1L);
        transactionCreateDTO.setAmount(new BigDecimal(100));
        assertEquals(1L, transactionCreateDTO.getCardId());
        assertEquals(new BigDecimal(100), transactionCreateDTO.getAmount());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionCreateDTO transactionCreateDTO1 = new TransactionCreateDTO(1L, BigDecimal.ZERO);
        TransactionCreateDTO transactionCreateDTO2 = new TransactionCreateDTO(1L, BigDecimal.ZERO);
        assertEquals(transactionCreateDTO1, transactionCreateDTO2);
        assertEquals(transactionCreateDTO1.hashCode(), transactionCreateDTO2.hashCode());
    }

    @Test
    public void testToString() {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO(1L, BigDecimal.ZERO);
        String transactionString = transactionCreateDTO.toString();
        assertTrue(transactionString.contains(String.valueOf(BigDecimal.ZERO)));
    }
}
