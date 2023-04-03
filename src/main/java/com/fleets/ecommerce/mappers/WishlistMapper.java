package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.Wishlist;
import com.fleets.ecommerce.models.Wishlist.WishlistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WishlistMapper {

    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);
    @Mapping(target = "userId", source = "user.id")
    WishlistDto toDto(Wishlist wishlist);


    @Mapping(target = "user.id", source = "userId")
     Wishlist toWishlist(WishlistDto dto);

    @Mapping(target = "userId", source = "user.id")
    List<WishlistDto> toDtos(List<Wishlist> wishlists);
}
