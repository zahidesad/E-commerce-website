package com.repository;

import com.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p.price FROM Price p WHERE p.product.id = :productId AND p.startDate <= CURRENT_DATE AND (p.endDate IS NULL OR p.endDate >= CURRENT_DATE)")
    BigDecimal findCurrentPriceByProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM Price p WHERE p.product.id = :productId")
    List<Price> findByProductId(@Param("productId") Long productId);
}