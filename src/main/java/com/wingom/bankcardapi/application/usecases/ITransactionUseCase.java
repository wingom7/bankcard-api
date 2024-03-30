package com.wingom.bankcardapi.application.usecases;

import com.wingom.bankcardapi.application.dto.TransactionOutputDTO;

import java.math.BigDecimal;

public interface ITransactionUseCase {
    TransactionOutputDTO createPurchase(String cardId, BigDecimal amount);
    TransactionOutputDTO getTransaction(String transactionId);
    TransactionOutputDTO annulTransaction(String transactionId, String cardId);
}
