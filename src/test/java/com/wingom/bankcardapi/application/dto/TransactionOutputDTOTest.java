package com.wingom.bankcardapi.application.dto;

import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionOutputDTOTest {
    @Test
    public void testNoArgsConstructor() {
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO();
        assertNotNull(transactionOutputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO("865654", "5654323456789098", LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertNotNull(transactionOutputDTO);
        assertEquals("865654", transactionOutputDTO.getTransactionNumber());
    }

    @Test
    public void testGettersAndSetters() {
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO();
        transactionOutputDTO.setTransactionNumber("876545");
        transactionOutputDTO.setCardNumber("9876765434345678");
        assertEquals("876545", transactionOutputDTO.getTransactionNumber());
        assertEquals("9876765434345678", transactionOutputDTO.getCardNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionOutputDTO transactionOutputDTO1 = new TransactionOutputDTO("465658", "689866543234569", LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        TransactionOutputDTO transactionOutputDTO2 = new TransactionOutputDTO("465658", "689866543234569", LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        assertEquals(transactionOutputDTO1, transactionOutputDTO2);
        assertEquals(transactionOutputDTO1.hashCode(), transactionOutputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO("345652", "589089865423453", LocalDateTime.now(), BigDecimal.ZERO, TransactionStatusEnum.PENDING);
        String transactionString = transactionOutputDTO.toString();
        assertTrue(transactionString.contains("345652"));
    }
}
