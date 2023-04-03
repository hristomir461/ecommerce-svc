package com.fleets.ecommerce.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Double totalPrice;

    private String sessionId;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> ordersProducts;
}
