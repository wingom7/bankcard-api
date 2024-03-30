package com.wingom.bankcardapi.domain.repositories;

import com.wingom.bankcardapi.domain.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>  {
    Boolean existsByCardNumber(String cardNumber);
    Optional<Card>  findByCardNumber(String cardNumber);
}
