package com.fleets.ecommerce.models.Wishlist;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WishlistDto {
    private Long id;

    private Long userId;

    private Date createdAt;

    private Long productId;

}
