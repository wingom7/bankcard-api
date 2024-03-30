package com.wingom.bankcardapi.domain.repositories;

import com.wingom.bankcardapi.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Boolean existsByTransactionNumber(String transactionNumber);
    Optional<Transaction> findByTransactionNumber(String transactionNumber);
}
