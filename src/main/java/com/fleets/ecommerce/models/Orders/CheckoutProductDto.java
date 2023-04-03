package com.fleets.ecommerce.models.Orders;

import lombok.Data;

@Data
public class CheckoutProductDto {

    private String name;

    private int quantity;

    private double price;

    private int userId;
}
