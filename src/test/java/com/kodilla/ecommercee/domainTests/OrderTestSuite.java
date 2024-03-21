package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    User user = new User(null, "Username", "Password", false, "abcabc", new ArrayList<>(), new ArrayList<>(), true);




    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveOrderTest() {
        //Given
        userRepository.save(user);

        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setUser(user);
        order.setActive(true);

        //When
        Order savedOrder = orderRepository.save(order);

        //Then
        Assertions.assertTrue(savedOrder.isActive());
        Assertions.assertEquals("Order-1", savedOrder.getOrderNumber());
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals(user.getId(), savedOrder.getUser().getId());

    }

    @Test
    public void findOrderByIdTest() {
        //Given
        userRepository.save(user);

        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        //When
        Optional<Order> foundOrderOptional = orderRepository.findById(savedOrder.getId());

        //Then
        Assertions.assertTrue(foundOrderOptional.isPresent());
        Order foundOrder = foundOrderOptional.get();
        Assertions.assertEquals("Order-1", foundOrder.getOrderNumber());
        Assertions.assertEquals(savedOrder.getId(), foundOrder.getId());
        Assertions.assertEquals(user.getId(), foundOrder.getUser().getId());
    }

    @Test
    public void updateOrderTest() {
        //Given
        userRepository.save(user);

        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        //When
        savedOrder.setOrderNumber("Order-3");
        Order updatedOrder = orderRepository.save(savedOrder);

        //Then
        Assertions.assertEquals(savedOrder.getId(), updatedOrder.getId());
        Assertions.assertEquals("Order-3", updatedOrder.getOrderNumber());
        Assertions.assertEquals(user.getId(), updatedOrder.getUser().getId());
    }

    @Test
    public void deleteOrderTest() {
        //Given
        userRepository.save(user);

        Order order = new Order();
        order.setOrderNumber("Order-1");
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        order.setActive(true);

        //When
        orderRepository.delete(savedOrder);

        //Then
        Optional<Order> deletedOrderOptional = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(deletedOrderOptional.isPresent());
        Assertions.assertFalse(deletedOrderOptional.get().isActive());
        Assertions.assertEquals(user.getId(), deletedOrderOptional.get().getUser().getId());
    }

    @Test
    public void findAllByActiveTrueTest() {
        //Given
        userRepository.save(user);

        Order activeOrder = new Order();
        activeOrder.setOrderNumber("Order-1");
        activeOrder.setUser(user);
        orderRepository.save(activeOrder);
        activeOrder.setActive(true);

        Order inactiveOrder = new Order();
        inactiveOrder.setOrderNumber("Order-2");
        inactiveOrder.setUser(user);
        orderRepository.save(inactiveOrder);
        inactiveOrder.setActive(false);

        //When
        List<Order> activeOrders = orderRepository.findAllByActiveTrue();

        //Then
        Assertions.assertTrue(activeOrders.get(0).isActive());
        Assertions.assertEquals(1, activeOrders.size());
        Assertions.assertEquals(user.getId(), activeOrders.get(0).getUser().getId());
    }
}

