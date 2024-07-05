package com.repository;

import com.model.Price;
import com.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByProductAndStartDateBeforeAndEndDateAfter(Product product, Date startDate, Date endDate);

    @Query("SELECT p.price FROM Price p WHERE p.product.id = :productId AND p.startDate <= CURRENT_DATE AND (p.endDate IS NULL OR p.endDate >= CURRENT_DATE)")
    BigDecimal findCurrentPriceByProductId(Long productId);
}