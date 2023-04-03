package com.fleets.ecommerce.repositories;

import com.fleets.ecommerce.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findByOrder_Id(Long orderId);
}