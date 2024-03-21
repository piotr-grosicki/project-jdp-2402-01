package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@DisplayName("Cart Entity Test Suite")
public class CartTestSuite {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    User user = new User( "Someone", "password123","blahblah");
    Group group = new Group("food", "things to eat");

    @AfterEach
    public void CleanUp() {
        cartRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testCreateCart() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(user);

        //When
        Cart savedCart = cartRepository.save(cart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertNotNull(retrievedCart.get().getId());
        assertTrue(retrievedCart.get().isActive());
    }

    @Test
    public void testDeleteCart() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(user);
        Cart savedCart = cartRepository.save(cart);

        //When
        cartRepository.delete(cart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertNotNull(retrievedCart.get().getId());
        assertFalse(retrievedCart.get().isActive());
    }

    @Test
    public void testGetCartById() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(user);
        Cart savedCart = cartRepository.save(cart);

        //When
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertTrue(retrievedCart.isPresent());
        assertEquals(retrievedCart.get().getId(), savedCart.getId());
    }

    @Test
    public void testAddProductToCart() {
        //Given
        userRepository.save(user);
        groupRepository.save(group);
        Cart cart = new Cart(user);
        Product pistachios = new Product("Pistachios", "200g bag", BigDecimal.valueOf(38.99), group);
        Product chocolate = new Product("Chocolate", "80g", BigDecimal.valueOf(4.99), group);
        productRepository.save(pistachios);
        productRepository.save(chocolate);
        cart.getProducts().add(pistachios);

        //When
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertTrue(retrievedCart.isPresent());
        assertTrue(retrievedCart.get().getProducts().get(1).isActive());
        assertEquals("Chocolate", retrievedCart.get().getProducts().get(1).getName());
    }

    @Test
    public void testRemoveProductFromCart() {
        //Given
        userRepository.save(user);
        groupRepository.save(group);
        Cart cart = new Cart(user);
        Product pistachios = new Product("Pistachios", "200g bag", BigDecimal.valueOf(38.99), group);
        Product chocolate = new Product("Chocolate", "80g", BigDecimal.valueOf(4.99), group);
        productRepository.save(pistachios);
        productRepository.save(chocolate);
        cart.getProducts().add(pistachios);
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);

        //When
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());
        List<Product> retrievedProducts = retrievedCart.get().getProducts();
        retrievedProducts.remove(pistachios);
        cart.setProducts(retrievedProducts);
        savedCart = cartRepository.save(cart);
        retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertFalse(retrievedCart.get().getProducts().contains(pistachios));
        assertEquals(1, retrievedCart.get().getProducts().size());
    }

}


