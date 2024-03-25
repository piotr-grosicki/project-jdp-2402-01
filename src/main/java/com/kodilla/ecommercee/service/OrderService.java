package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAllByActiveTrue();
    }

    public Order getOrderById(final Long orderId) throws OrderNotFoundException {
        return orderRepository.findByIdAndActiveTrue(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public void createOrderFromCart(final Long cartId) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId).orElseThrow(CartNotFoundException::new);
        Order order = new Order();
        order.setOrderNumber("order/" + cart.getId());
        order.setUser(cart.getUser());
        order.setProducts(new ArrayList<>(cart.getProducts()));
        order.setActive(true);
        cart.setActive(false);
        cartRepository.save(cart);
        orderRepository.save(order);
    }

    public Order updateOrder(Long orderId, Order order) throws OrderNotFoundException {
        Order updatedOrder = this.getOrderById(orderId);
        updatedOrder.setOrderNumber(order.getOrderNumber());
        updatedOrder.setUser(order.getUser());
        updatedOrder.setProducts(order.getProducts());
        updatedOrder.setActive(order.isActive());
        return orderRepository.save(updatedOrder);
    }

    public void deleteOrder(final Long orderId) throws OrderNotFoundException {
        orderRepository.delete(orderRepository.findByIdAndActiveTrue(orderId)
                .orElseThrow(OrderNotFoundException::new));
    }
}
