package com.fleets.ecommerce.models.Orders;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Long userId;

    private String sessionId;

    private double totalPrice;

}
