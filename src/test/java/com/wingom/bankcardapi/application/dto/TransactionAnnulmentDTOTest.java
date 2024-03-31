package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAnnulmentDTOTest {
    @Test
    public void testNoArgsConstructor() {
        TransactionAnnulmentDTO transactionAnnulmentDTO = new TransactionAnnulmentDTO();
        assertNotNull(transactionAnnulmentDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionAnnulmentDTO transactionAnnulmentDTO = new TransactionAnnulmentDTO("5654323456789098", "345688");
        assertNotNull(transactionAnnulmentDTO);
        assertEquals("5654323456789098", transactionAnnulmentDTO.getCardId());
    }

    @Test
    public void testGettersAndSetters() {
        TransactionAnnulmentDTO transactionAnnulmentDTO = new TransactionAnnulmentDTO();
        transactionAnnulmentDTO.setCardId("9876765434345678");
        transactionAnnulmentDTO.setTransactionId("654368");
        assertEquals("9876765434345678", transactionAnnulmentDTO.getCardId());
        assertEquals("654368", transactionAnnulmentDTO.getTransactionId());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionAnnulmentDTO transactionAnnulmentDTO1 = new TransactionAnnulmentDTO("5654323456789098", "654345");
        TransactionAnnulmentDTO transactionAnnulmentDTO2 = new TransactionAnnulmentDTO("5654323456789098", "654345");
        assertEquals(transactionAnnulmentDTO1, transactionAnnulmentDTO2);
        assertEquals(transactionAnnulmentDTO1.hashCode(), transactionAnnulmentDTO2.hashCode());
    }

    @Test
    public void testToString() {
        TransactionAnnulmentDTO transactionAnnulmentDTO = new TransactionAnnulmentDTO("6543234568909865", "890654");
        String transactionString = transactionAnnulmentDTO.toString();
        assertTrue(transactionString.contains("6543234568909865"));
    }
}
