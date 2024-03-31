package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionPurchaseDTOTest {
    @Test
    public void testNoArgsConstructor() {
        TransactionPurchaseDTO transactionPurchaseDTO = new TransactionPurchaseDTO();
        assertNotNull(transactionPurchaseDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionPurchaseDTO transactionPurchaseDTO = new TransactionPurchaseDTO("5654323456789098", BigDecimal.ZERO);
        assertNotNull(transactionPurchaseDTO);
        assertEquals("5654323456789098", transactionPurchaseDTO.getCardId());
    }

    @Test
    public void testGettersAndSetters() {
        TransactionPurchaseDTO transactionPurchaseDTO = new TransactionPurchaseDTO();
        transactionPurchaseDTO.setCardId("9876765434345678");
        transactionPurchaseDTO.setPrice(new BigDecimal(100));
        assertEquals("9876765434345678", transactionPurchaseDTO.getCardId());
        assertEquals(new BigDecimal(100), transactionPurchaseDTO.getPrice());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionPurchaseDTO transactionPurchaseDTO1 = new TransactionPurchaseDTO("5654323456789098", BigDecimal.ZERO);
        TransactionPurchaseDTO transactionPurchaseDTO2 = new TransactionPurchaseDTO("5654323456789098", BigDecimal.ZERO);
        assertEquals(transactionPurchaseDTO1, transactionPurchaseDTO2);
        assertEquals(transactionPurchaseDTO1.hashCode(), transactionPurchaseDTO2.hashCode());
    }

    @Test
    public void testToString() {
        TransactionPurchaseDTO transactionPurchaseDTO = new TransactionPurchaseDTO("6543234568909865", BigDecimal.ZERO);
        String transactionString = transactionPurchaseDTO.toString();
        assertTrue(transactionString.contains("6543234568909865"));
    }
}
