package com.wingom.bankcardapi.domain.repositories;

import com.wingom.bankcardapi.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByProductCode(String productCode);
    Optional<Product> findByProductCode(String productCode);

}
