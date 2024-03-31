package com.wingom.bankcardapi.application.dto;

import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionDTOTest {
    @Test
    public void testNoArgsConstructor() {
        TransactionDTO transactionDTO = new TransactionDTO();
        assertNotNull(transactionDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionDTO transactionDTO = new TransactionDTO(1L, "865654", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertNotNull(transactionDTO);
        assertEquals("865654", transactionDTO.getTransactionNumber());
    }

    @Test
    public void testGettersAndSetters() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setTransactionNumber("876545");
        assertEquals(1L, transactionDTO.getId());
        assertEquals("876545", transactionDTO.getTransactionNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionDTO transactionDTO1 = new TransactionDTO(1L, "465658", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        TransactionDTO transactionDTO2 = new TransactionDTO(1L, "465658", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertEquals(transactionDTO1, transactionDTO2);
        assertEquals(transactionDTO1.hashCode(), transactionDTO2.hashCode());
    }

    @Test
    public void testToString() {
        TransactionDTO transactionDTO = new TransactionDTO(1L, "345652", null, LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        String transactionString = transactionDTO.toString();
        assertTrue(transactionString.contains("345652"));
    }
}
