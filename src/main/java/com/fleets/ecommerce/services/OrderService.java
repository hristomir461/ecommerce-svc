package com.fleets.ecommerce.services;

import com.fleets.ecommerce.entities.*;
import com.fleets.ecommerce.mappers.OrderMapper;
import com.fleets.ecommerce.mappers.ProductMapper;
import com.fleets.ecommerce.models.Orders.CheckoutProductDto;
import com.fleets.ecommerce.models.Orders.OrderDto;
import com.fleets.ecommerce.models.Products.ProductDto;
import com.fleets.ecommerce.repositories.*;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Value("${baseUrl}")
    private String baseURL;

    @Value("${stripeSecretKey}")
    private String apiKey;

    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutProductDto checkoutProductDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("bgn")
                .setUnitAmount((long)(checkoutProductDto.getPrice()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutProductDto.getName())
                                .build())
                .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CheckoutProductDto checkoutProductDto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutProductDto))
                // set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutProductDto.getQuantity())))
                .build();
    }

    public Session createSession(List<CheckoutProductDto> checkoutProductDtoList) throws StripeException {

        // supply success and failure url for stripe
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        // set the private key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        // for each product compute SessionCreateParams.LineItem
        for (CheckoutProductDto checkoutItemDto : checkoutProductDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }

        // build the session param
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    public void create(OrderDto request) {
        Order order = OrderMapper.INSTANCE.toOrder(request);
        order.setCreatedAt(new Date());
        orderRepository.save(order);
        List<Cart> carts = cartRepository.findAllByUser_Id(request.getUserId());
        for (var product : carts) {
            var orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product.getProduct());
            orderProduct.setQuantity(product.getQuantity());
            orderProductRepository.save(orderProduct);
        }
    }
    public List<ProductDto> getAllProducts(Long userId){
        Order order = orderRepository.findByUser_Id(userId);
        List<Product> products = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        order.getOrdersProducts().forEach(x -> ids.add(x.getProduct().getId()));
        productRepository.findAllById(ids).forEach(products::add);
        return ProductMapper.INSTANCE.toDtos(products);
    }
}
