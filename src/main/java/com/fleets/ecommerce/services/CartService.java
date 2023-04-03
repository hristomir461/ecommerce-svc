package com.fleets.ecommerce.services;

import com.fleets.ecommerce.entities.Cart;
import com.fleets.ecommerce.mappers.CartMapper;
import com.fleets.ecommerce.models.Cart.CartDto;
import com.fleets.ecommerce.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart create(CartDto request) {
        Cart cart = CartMapper.INSTANCE.toCart(request);
        cart.setCreatedAt(new Date());
        return cartRepository.save(cart);
    }

    public List<Cart> getAll(Long userId) {
        return cartRepository.findAllByUser_Id(userId);
    }
    public void deleteById(Long userId, Long productId){
        cartRepository.deleteById(cartRepository.findByUser_IdAndProduct_Id(userId, productId).getId());
    }
}