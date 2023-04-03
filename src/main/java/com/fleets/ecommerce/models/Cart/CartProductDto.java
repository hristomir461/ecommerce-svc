package com.fleets.ecommerce.models.Cart;

import lombok.Data;

import java.util.Date;

@Data
public class CartProductDto {
    private String name;

    private String imageUrl;

    private double price;

    private int quantity;

    private String category;

    private Date createdAt;
}
