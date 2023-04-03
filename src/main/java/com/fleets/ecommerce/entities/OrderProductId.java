package com.fleets.ecommerce.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class OrderProductId implements Serializable {

    private Long orderId;

    private Long productId;

}