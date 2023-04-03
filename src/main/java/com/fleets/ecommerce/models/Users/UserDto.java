package com.fleets.ecommerce.models.Users;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String role;
}
