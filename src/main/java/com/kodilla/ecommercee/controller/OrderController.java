package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orderMapper.mapToOrderDtoList(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") Long orderId)
            throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.getOrderById(orderId)));
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<Void> createOrderFromCart(@PathVariable Long cartId) throws CartNotFoundException {
        orderService.createOrderFromCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) throws OrderNotFoundException {
        Order order = orderMapper.mapToOrder(orderDto);
        Order savedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(savedOrder));
    }

    @PutMapping("/add/{orderId}/{productId}")
    public ResponseEntity<OrderDto> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId)
            throws OrderNotFoundException, ProductNotFoundException {
        Order order = orderService.addProductToOrder(orderId, productId);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @DeleteMapping("/delete/{orderId}/{productId}")
    public ResponseEntity<OrderDto> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId)
            throws OrderNotFoundException, ProductNotFoundException {
        Product product = orderService.getProductFromOrder(orderId, productId);
        Order order = orderService.deleteProductFromOrder(orderId, product);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
