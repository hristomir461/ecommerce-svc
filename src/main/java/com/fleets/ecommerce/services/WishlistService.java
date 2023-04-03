package com.fleets.ecommerce.services;

import com.fleets.ecommerce.entities.Product;
import com.fleets.ecommerce.entities.Wishlist;
import com.fleets.ecommerce.mappers.WishlistMapper;
import com.fleets.ecommerce.models.Wishlist.WishlistDto;
import com.fleets.ecommerce.repositories.ProductRepository;
import com.fleets.ecommerce.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    public Wishlist create(WishlistDto request) {
        Wishlist wishlist = WishlistMapper.INSTANCE.toWishlist(request);
        wishlist.setCreatedAt(new Date());
        return wishlistRepository.save(wishlist);
    }

    public void removeProduct(WishlistDto request) {
        Wishlist wishlist = wishlistRepository.findByUser_Id(request.getUserId());
        var products = wishlist.getProducts();
        products.remove(productRepository.getById(request.getProductId()));
        wishlist.setProducts(products);
         wishlistRepository.save(wishlist);
    }
    public Wishlist addProduct(WishlistDto request) {
        Wishlist wishlist = wishlistRepository.findByUser_Id(request.getUserId());
        var products = wishlist.getProducts();
        products.add(productRepository.getById(request.getProductId()));
        wishlist.setProducts(products);
        return wishlistRepository.save(wishlist);
    }

    public List<Product> getAllProducts(Long userId){
        Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
        return wishlist.getProducts();
    }

    public boolean existByUserId(Long userId){
        if(wishlistRepository.findByUser_Id(userId) != null){
            return true;
        }
        return false;
    }
}
