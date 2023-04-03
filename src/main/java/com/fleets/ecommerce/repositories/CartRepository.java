package com.fleets.ecommerce.repositories;

import com.fleets.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userId);

    Cart findByUser_IdAndProduct_Id(Long userId, Long productId);
}