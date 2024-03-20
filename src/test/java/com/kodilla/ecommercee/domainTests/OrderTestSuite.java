package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    public void testSaveOrder() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setActive(true);

        //When
        Order savedOrder = orderRepository.save(order);

        //Then
        assertTrue(savedOrder.isActive());
        assertEquals("Order-1", savedOrder.getOrderNumber());
        assertNotNull(savedOrder.getId());
    }

    @Test
    public void testFindOrderById() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);

        //When
        Optional<Order> foundOrderOptional = orderRepository.findById(savedOrder.getId());

        //Then
        assertTrue(foundOrderOptional.isPresent());
        Order foundOrder = foundOrderOptional.get();
        assertEquals("Order-1", foundOrder.getOrderNumber());
        assertEquals(savedOrder.getId(), foundOrder.getId());
    }

    @Test
    public void testUpdateOrder() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);

        //When
        savedOrder.setOrderNumber("Order-3");
        Order updatedOrder = orderRepository.save(savedOrder);

        //Then
        assertEquals(savedOrder.getId(), updatedOrder.getId());
        assertEquals("Order-3", updatedOrder.getOrderNumber());
    }

    @Test
    public void testDeleteOrder() {
        //Given
        Order order = new Order();
        order.setOrderNumber("Order-1");
        Order savedOrder = orderRepository.save(order);
        order.setActive(true);

        //When
        orderRepository.delete(savedOrder);
        Optional<Order> deletedOrderOptional = orderRepository.findById(savedOrder.getId());

        //Then
        assertTrue(deletedOrderOptional.isPresent());
        assertFalse(deletedOrderOptional.get().isActive());
    }

    @Test
    public void testFindAllByActiveTrue() {
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
        assertTrue(activeOrders.get(0).isActive());
        assertEquals(1, activeOrders.size());
    }
}
