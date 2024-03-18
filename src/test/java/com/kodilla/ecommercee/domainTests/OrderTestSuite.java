package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void saveOrderTest() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setActive(true);

        //When
        Order savedOrder = orderRepository.save(order);

        //Then
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals("Order-1", savedOrder.getOrderNumber());
        Assertions.assertTrue(savedOrder.isActive());
    }

    @Test
    void findOrderByIdTest() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);

        //When
        Optional<Order> foundOrderOptional = orderRepository.findById(savedOrder.getId());

        //Then
        Assertions.assertTrue(foundOrderOptional.isPresent());
        Order foundOrder = foundOrderOptional.get();
        Assertions.assertEquals(savedOrder.getId(), foundOrder.getId());
        Assertions.assertEquals("Order-1", foundOrder.getOrderNumber());
    }

    @Test
    void updateOrderTest() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);

        //When
        savedOrder.setOrderNumber("Order-3");
        Order updatedOrder = orderRepository.save(savedOrder);

        //Then
        Assertions.assertEquals(savedOrder.getId(), updatedOrder.getId());
        Assertions.assertEquals("Order-3", updatedOrder.getOrderNumber());
    }

    @Test
    void deleteOrderTest() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);
        order.setActive(true);

        //When
        orderRepository.delete(savedOrder);

        //Then
        Optional<Order> deletedOrderOptional = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(deletedOrderOptional.isPresent());
        Assertions.assertFalse(deletedOrderOptional.get().isActive());
    }

    @Test
    void findAllByActiveTrueTest() {
        //Given
        Order activeOrder = new Order();
        activeOrder.setOrderNumber("Order-1");
        orderRepository.save(activeOrder);
        activeOrder.setActive(true);

        Order inactiveOrder = new Order();
        inactiveOrder.setOrderNumber("Order-2");
        orderRepository.save(inactiveOrder);
        inactiveOrder.setActive(false);

        //When
        List<Order> activeOrders = orderRepository.findAllByActiveTrue();

        //Then
        Assertions.assertEquals(1, activeOrders.size());
        Assertions.assertTrue(activeOrders.get(0).isActive());
    }
}

