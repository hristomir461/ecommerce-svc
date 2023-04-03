package com.fleets.ecommerce.repositories;

import com.fleets.ecommerce.entities.User;
import com.fleets.ecommerce.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findByUser_Id(Long userId);
}
