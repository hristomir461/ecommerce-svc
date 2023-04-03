package com.fleets.ecommerce.models.Cart;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class CartDto {
    private Long userId;

    private int quantity;

    private Long productId;
}