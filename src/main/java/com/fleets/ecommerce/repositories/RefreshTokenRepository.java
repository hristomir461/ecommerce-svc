package com.fleets.ecommerce.repositories;



import com.fleets.ecommerce.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByValue(String value);

    void deleteByValue(String value);
}
