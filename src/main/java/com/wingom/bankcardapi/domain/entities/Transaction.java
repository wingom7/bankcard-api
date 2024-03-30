package com.wingom.bankcardapi.domain.entities;

import com.wingom.bankcardapi.domain.enums.TransactionStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionNumber;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private LocalDateTime transactionDate;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status = TransactionStatusEnum.PENDING;

}

