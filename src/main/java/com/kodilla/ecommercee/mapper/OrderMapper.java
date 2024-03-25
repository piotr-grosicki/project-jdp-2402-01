package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public final OrderService orderService;

    public OrderDto mapToOrderDto(Order order) {
        List<Long> orderProductsIds = order.getProducts().stream()
                .map(Product::getId)
                .toList();
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getId(),
                orderProductsIds,
                order.isActive());
    }

    public List<OrderDto> mapToOrderDtoList(List<Order> orders) {
        List<OrderDto> orderDtoList = orders.stream()
                .map(this::mapToOrderDto)
                .toList();
        return orderDtoList;
    }

    public Order mapToOrder(OrderDto orderDto) throws OrderNotFoundException {
        User user = orderService.getOrderById(orderDto.getId()).getUser();
        List<Product> orderProductsList = orderService.getOrderById(orderDto.getId()).getProducts();
        return new Order(orderDto.getId(),
                orderDto.getOrderNumber(),
                user,
                orderProductsList,
                orderDto.isActive());
    }
}
