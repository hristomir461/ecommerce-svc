package com.fleets.ecommerce.controllers;

import com.fleets.ecommerce.mappers.CartMapper;
import com.fleets.ecommerce.models.Cart.CartDto;
import com.fleets.ecommerce.models.Cart.CartProductDto;
import com.fleets.ecommerce.models.Users.UserDto;
import com.fleets.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("#user.id == #userId")
    @PostMapping("/add")
    public ResponseEntity add(@AuthenticationPrincipal UserDto user, @RequestParam Long userId, @RequestBody CartDto request) {
             cartService.create(request);
            return ResponseEntity.ok().build();
    }
    @PreAuthorize("#user.id == #userId")
    @GetMapping
    public ResponseEntity<List<CartProductDto>> cart(@AuthenticationPrincipal UserDto user, @RequestParam Long userId) {
        var products = CartMapper.INSTANCE.toCartProducts(cartService.getAll(userId));
        return ResponseEntity.ok().body(products);
    }
    @PreAuthorize("#user.id == #userId")
    @PostMapping("/remove/{id}")
    public ResponseEntity removeProduct(@AuthenticationPrincipal UserDto user, @RequestParam Long userId, @PathVariable Long id) {
            cartService.deleteById(userId, id);
            return ResponseEntity.ok().build();
    }
}
