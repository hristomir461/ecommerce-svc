package com.fleets.ecommerce.controllers;


import com.fleets.ecommerce.entities.User;
import com.fleets.ecommerce.models.Users.LoginDto;
import com.fleets.ecommerce.models.Users.SignupDto;
import com.fleets.ecommerce.models.Users.TokenDto;
import com.fleets.ecommerce.security.jwt.JwtHelper;
import com.fleets.ecommerce.services.RefreshTokenService;
import com.fleets.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserService usersService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto userRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String email = authentication.getName();
        var user = usersService.getByEmail(email);
        String accessToken = jwtHelper.generateAccessToken(user);
        String refreshToken = refreshTokenService.save(user);

        var tokenResponse = new TokenDto(accessToken, refreshToken);

        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("signup")
    public ResponseEntity<TokenDto> signup(@Valid @RequestBody SignupDto userRequest) {
        var user = usersService.save(userRequest);

        String accessToken = jwtHelper.generateAccessToken(user);
        String refreshToken = refreshTokenService.save(user);

        var tokenResponse = new TokenDto(accessToken, refreshToken);
        return ResponseEntity.ok().body(tokenResponse);
    }
    @PostMapping("recreate-access-token")
    public ResponseEntity<TokenDto> accessToken(@RequestBody TokenDto token) {
        var refreshToken = token.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshToken)) {

            User user = usersService.getById(jwtHelper.getUserIdFromRefreshToken(refreshToken));
            String accessToken = jwtHelper.generateAccessToken(user);

            var tokenResponse = new TokenDto(accessToken);
            return ResponseEntity.ok().body(tokenResponse);
        }

        throw new BadCredentialsException("invalid token");
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody TokenDto token) {
        var refreshToken = token.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshToken) && refreshTokenService.Exist(refreshToken)) {

            refreshTokenService.delete(refreshToken);
            return ResponseEntity.ok().build();
        }

        throw new BadCredentialsException("invalid or no such token");
    }
}
