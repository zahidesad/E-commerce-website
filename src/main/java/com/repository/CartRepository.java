package com.repository;

import com.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByEmail(String email);
    Optional<Cart> findByEmailAndProductId(String email, int productId);
    void deleteByProductId(Long productId);
}
