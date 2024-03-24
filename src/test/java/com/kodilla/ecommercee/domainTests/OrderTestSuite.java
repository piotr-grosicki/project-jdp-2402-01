package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    User user = new User( "Username", "Password", "abcabc");

    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testSaveOrder() {
        //Given
        userRepository.save(user);
        Order order = new Order("Order-1", user);

        //When
        Order savedOrder = orderRepository.save(order);

        //Then
        assertTrue(savedOrder.isActive());
        assertEquals("Order-1", savedOrder.getOrderNumber());
        assertNotNull(savedOrder.getId());
        assertEquals(user.getId(), savedOrder.getUser().getId());

    }

    @Test
    public void testFindOrderById() {
        //Given
        userRepository.save(user);
        Order order = new Order("Order-1", user);
        orderRepository.save(order);

        //When
        Optional<Order> retrievedOrder = orderRepository.findById(order.getId());

        //Then
        assertTrue(retrievedOrder.isPresent());
        assertEquals("Order-1", retrievedOrder.get().getOrderNumber());
        assertEquals(order.getId(), retrievedOrder.get().getId());
        assertEquals(user.getId(), retrievedOrder.get().getUser().getId());
    }

    @Test
    public void testUpdateOrder() {
        //Given
        userRepository.save(user);
        Order order = new Order("Order-1", user);
        orderRepository.save(order);

        //When
        order.setOrderNumber("Order-3");
        orderRepository.save(order);
        Optional<Order> retrievedOrder = orderRepository.findById(order.getId());

        //Then
        assertTrue(retrievedOrder.isPresent());
        assertEquals(order.getId(), retrievedOrder.get().getId());
        assertEquals("Order-3", retrievedOrder.get().getOrderNumber());
        assertEquals(user.getId(), retrievedOrder.get().getUser().getId());
    }

    @Test
    public void testDeleteOrder() {
        //Given
        userRepository.save(user);
        Order order = new Order("Order-1", user);
        orderRepository.save(order);

        //When
        orderRepository.delete(order);
        Optional<Order> retrievedOrder = orderRepository.findById(order.getId());

        //Then
        assertTrue(retrievedOrder.isPresent());
        assertFalse(retrievedOrder.get().isActive());
        assertEquals(user.getId(), retrievedOrder.get().getUser().getId());
    }

    @Test
    public void testFindAllByActiveTrue() {
        //Given
        userRepository.save(user);
        Order activeOrder = new Order("Order-1", user);
        orderRepository.save(activeOrder);
        Order inactiveOrder = new Order("Order-2", user);
        inactiveOrder.setActive(false);
        orderRepository.save(inactiveOrder);

        //When
        List<Order> retrievedActiveOrders = orderRepository.findAllByActiveTrue();

        //Then
        assertTrue(retrievedActiveOrders.get(0).isActive());
        assertEquals(1, retrievedActiveOrders.size());
        assertEquals(user.getId(), retrievedActiveOrders.get(0).getUser().getId());
    }

    @Test
    public void testAddProductsToOrder(){
        //Given
        userRepository.save(user);
        Order order = new Order("Order-1", user);
        Group group = new Group("food", "things to eat");
        groupRepository.save(group);
        Product pistachios = new Product("Pistachios", "200g bag", BigDecimal.valueOf(38.99), group);
        Product chocolate = new Product("Chocolate", "80g", BigDecimal.valueOf(4.99), group);
        productRepository.save(pistachios);
        productRepository.save(chocolate);
        List<Product> products = new ArrayList<>();
        products.add(pistachios);
        products.add(chocolate);

        //When
        order.setProducts(products);
        orderRepository.save(order);
        Optional<Order> retrievedOrder = orderRepository.findById(order.getId());

        //Then
        assertFalse(retrievedOrder.get().getProducts().isEmpty());
        assertEquals(2, retrievedOrder.get().getProducts().size());
    }
}

