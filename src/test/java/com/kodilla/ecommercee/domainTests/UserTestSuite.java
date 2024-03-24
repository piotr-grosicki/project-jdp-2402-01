package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
        orderRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        // Given
        User user = new User(null, "testUsername", "testPassword",
                false, "testApiKey", new ArrayList<>(), new ArrayList<>(), true);
        User savedUser = userRepository.save(user);

        //When
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        //Then
        assertTrue(retrievedUser.isPresent());
        assertEquals("testUsername", retrievedUser.get().getUsername());
        assertEquals("testPassword", retrievedUser.get().getPassword());

    }

    @Test
    public void testUpdateUser() {
        // Given
        User user = new User(null, "testUsername", "testPassword",
                false, "testApiKey", new ArrayList<>(), new ArrayList<>(), true);

        User savedUser = userRepository.save(user);

        //When
        savedUser.setUsername("newUsername");
        savedUser.setPassword("newPassword");
        userRepository.save(savedUser);
        Optional<User> updatedUser = userRepository.findById(savedUser.getId());

        //Then
        assertTrue(updatedUser.isPresent());
        assertEquals("newUsername", updatedUser.get().getUsername());
        assertEquals("newPassword", updatedUser.get().getPassword());

    }

    @Test
    public void testDeleteUser() {
        // Given
        User user = new User(null, "testUsername", "testPassword",
                false, "testApiKey", new ArrayList<>(), new ArrayList<>(), true);
        User savedUser = userRepository.save(user);

        //When
        userRepository.delete(savedUser);
        Optional<User> deactivatedUser = userRepository.findById(savedUser.getId());

        //Then
        assertTrue(deactivatedUser.isPresent());
        assertFalse(deactivatedUser.get().isActive());

    }

    @Test
    public void testSaveUserWithOrder() {
        // Given
        User user = new User(null, "testUsername", "testPassword",
                false, "testApiKey", new ArrayList<>(), new ArrayList<>(), true);
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber("1");
        user.getOrders().add(order);

        // When
        User savedUser = userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        // Then
        assertTrue(retrievedUser.isPresent());
        assertFalse(retrievedUser.get().getOrders().isEmpty());

    }

    @Test
    public void testSaveUserWithCart() {
        //Given
        User user = new User(null, "testUsername", "testPassword",
                false, "testApiKey", new ArrayList<>(), new ArrayList<>(), true);
        Cart cart = new Cart();
        cart.setUser(user);
        user.getCarts().add(cart);

        //When
        User savedUser = userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        //Then
        assertTrue(retrievedUser.isPresent());
        assertFalse(retrievedUser.get().getCarts().isEmpty());
        assertNotNull(retrievedUser.get());

    }
}
