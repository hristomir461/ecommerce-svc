package com.fleets.ecommerce.controllers;

import com.fleets.ecommerce.models.Orders.CheckoutProductDto;
import com.fleets.ecommerce.models.Orders.OrderDto;
import com.fleets.ecommerce.models.Orders.StripeDto;
import com.fleets.ecommerce.models.Products.ProductDto;
import com.fleets.ecommerce.models.Users.UserDto;
import com.fleets.ecommerce.services.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeDto> checkoutList(@RequestBody List<CheckoutProductDto> checkoutProductDtoList) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutProductDtoList);
        StripeDto stripeResponse = new StripeDto(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<StripeDto>(stripeResponse, HttpStatus.OK);
    }
    @PreAuthorize("#user.id == #request.userId")
    @PostMapping("/make")
    public ResponseEntity make(@AuthenticationPrincipal UserDto user, @RequestBody OrderDto request) {
            orderService.create(request);
            return ResponseEntity.ok().build();
    }

    @PreAuthorize("#user.id == #userId")
    @GetMapping
    public ResponseEntity<List<ProductDto>> cart(@AuthenticationPrincipal UserDto user, @RequestParam Long userId) {
        var products = orderService.getAllProducts(userId);
        return ResponseEntity.ok().body(products);
    }
}
