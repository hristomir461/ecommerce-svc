package com.fleets.ecommerce.models.Users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
