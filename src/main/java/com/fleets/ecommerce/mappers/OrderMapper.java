package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.Order;
import com.fleets.ecommerce.models.Orders.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    @Mapping(target = "user.id", source = "userId")
    Order toOrder(OrderDto dto);

    @Mapping(target = "userId", source = "user.id")
    List<OrderDto> toDtos(List<Order> Orders);
}