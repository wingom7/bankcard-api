package com.wingom.bankcardapi.domain.entities;

import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTest {

    @Test
    public void testNoArgsConstructor() {
        Transaction transaction = new Transaction();
        assertNotNull(transaction);
    }

    @Test
    public void testAllArgsConstructor() {
        Transaction transaction = new Transaction(1L, "865654", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertNotNull(transaction);
        assertEquals("865654", transaction.getTransactionNumber());
    }

    @Test
    public void testGettersAndSetters() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionNumber("876545");
        assertEquals(1L, transaction.getId());
        assertEquals("876545", transaction.getTransactionNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        Transaction transaction1 = new Transaction(1L, "465658", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        Transaction transaction2 = new Transaction(1L, "465658", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testToString() {
        Transaction transaction = new Transaction(1L, "345652", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        String transactionString = transaction.toString();
        assertTrue(transactionString.contains("345652"));
    }
}
