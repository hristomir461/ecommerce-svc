package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.Cart;
import com.fleets.ecommerce.models.Cart.CartDto;
import com.fleets.ecommerce.models.Cart.CartProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user.id", source = "userId")
    Cart toCart(CartDto dto);

    default List<CartProductDto> toCartProducts(List<Cart> carts){
        var cartProducts = new ArrayList<CartProductDto>();
        for (var item:carts) {
            var cartProduct = new CartProductDto();
            var product = item.getProduct();
            cartProduct.setQuantity(item.getQuantity());
            cartProduct.setName(product.getName());
            cartProduct.setPrice(product.getPrice() * item.getQuantity());
            //cartProduct.setImageUrl(product.getImageUrl());
            cartProduct.setCategory(product.getCategory().getName());
            cartProduct.setCreatedAt(item.getCreatedAt());
            cartProducts.add(cartProduct);
        }
        return cartProducts;
    }

}
