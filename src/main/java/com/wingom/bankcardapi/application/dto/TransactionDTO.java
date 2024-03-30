package com.wingom.bankcardapi.application.dto;

import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private String transactionNumber;
    private Long cardId;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private TransactionStatusEnum status;
}