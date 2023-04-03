package com.fleets.ecommerce.models.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
