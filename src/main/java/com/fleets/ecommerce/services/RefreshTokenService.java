package com.fleets.ecommerce.services;


import com.fleets.ecommerce.entities.RefreshToken;
import com.fleets.ecommerce.entities.User;
import com.fleets.ecommerce.repositories.RefreshTokenRepository;
import com.fleets.ecommerce.security.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtHelper jwtHelper;

    public String save(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        var value = jwtHelper.generateRefreshToken(user);
        refreshToken.setValue(value);
        refreshTokenRepository.save(refreshToken);
        return value;
    }

    public Boolean Exist(String refreshToken){
        return refreshTokenRepository.existsByValue(refreshToken);
    }

    public void delete(String refreshToken){
        refreshTokenRepository.deleteByValue(refreshToken);
    }
}
