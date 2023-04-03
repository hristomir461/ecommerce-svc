package com.fleets.ecommerce.repositories;

import com.fleets.ecommerce.entities.Order;
import com.fleets.ecommerce.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUser_Id(Long userId);
}