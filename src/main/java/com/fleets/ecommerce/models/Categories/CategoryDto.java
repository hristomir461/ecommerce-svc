package com.fleets.ecommerce.models.Categories;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    @NotBlank
    private String description;
}
