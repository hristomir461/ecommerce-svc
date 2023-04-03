package com.fleets.ecommerce.controllers;

import com.fleets.ecommerce.exceptions.UserNotFoundException;
import com.fleets.ecommerce.mappers.ProductMapper;
import com.fleets.ecommerce.mappers.WishlistMapper;
import com.fleets.ecommerce.models.Products.ProductDto;
import com.fleets.ecommerce.models.Users.UserDto;
import com.fleets.ecommerce.models.Wishlist.WishlistDto;
import com.fleets.ecommerce.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PreAuthorize("#user.id == #request.userId")
    @PostMapping("/add")
    public ResponseEntity<WishlistDto> add(@AuthenticationPrincipal UserDto user, @RequestBody WishlistDto request) {
        if(!wishlistService.existByUserId(request.getUserId())){
            var wishlist = wishlistService.create(request);
            var response = WishlistMapper.INSTANCE.toDto(wishlist);
            return ResponseEntity.ok().body(response);
        }else{
            var wishlist = wishlistService.addProduct(request);
            var response = WishlistMapper.INSTANCE.toDto(wishlist);
            return ResponseEntity.ok().body(response);
        }
    }
    @PreAuthorize("#user.id == #userId")
    @GetMapping
    public ResponseEntity<List<ProductDto>> wishlist(@AuthenticationPrincipal UserDto user, @RequestParam Long userId) {
            var products = wishlistService.getAllProducts(userId);
            var response = ProductMapper.INSTANCE.toDtos(products);
            return ResponseEntity.ok().body(response);
    }
    @PreAuthorize("#user.id == #request.userId")
    @PostMapping("/remove")
    public ResponseEntity removeProduct(@AuthenticationPrincipal UserDto user, @RequestBody WishlistDto request) {
        if(wishlistService.existByUserId(request.getUserId())){
             wishlistService.removeProduct(request);
            return ResponseEntity.ok().build();
        }
        throw new UserNotFoundException("There is no user with id " + request.getUserId());
    }
}
