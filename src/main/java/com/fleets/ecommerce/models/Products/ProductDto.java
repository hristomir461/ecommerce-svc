package com.fleets.ecommerce.models.Products;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private double price;

    private String description;

    private Long categoryId;

    private Long imageId;
}
