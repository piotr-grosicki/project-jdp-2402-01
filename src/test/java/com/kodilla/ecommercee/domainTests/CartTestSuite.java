package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    User user = new User(null, "Someone", "password123", false, "blahblah", new ArrayList<>(), new ArrayList<>(), true);

    @AfterEach
    void tearDown() {
        cartRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createCartTest() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);

        //When
        Cart savedCart = cartRepository.save(cart);

        //Then
        Assertions.assertNotNull(savedCart.getId());
        Assertions.assertTrue(savedCart.isActive());
    }

    @Test
    void deleteCartTest() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        Cart savedCart = cartRepository.save(cart);

        //When
        cartRepository.delete(cart);

        //Then
        Assertions.assertNotNull(savedCart.getId());
        Assertions.assertFalse(savedCart.isActive());
    }

    @Test
    void getCartByIdTest() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        Cart savedCart = cartRepository.save(cart);

        //When
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        Assertions.assertEquals(retrievedCart.get().getId(), savedCart.getId());
    }


    @Test
    void addProductToCartTest() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        Product pistachios = new Product(null, "Pistachios", "200g bag", BigDecimal.valueOf(38.99), new Group(), carts, new ArrayList<>(), true);
        Product chocolate = new Product(null, "Chocolate", "80g", BigDecimal.valueOf(4.99), new Group(), carts, new ArrayList<>(), true);
        Group group = new Group(null, "food", "things to eat", new ArrayList<>(), true);
        pistachios.setGroup(group);
        chocolate.setGroup(group);
        //productRepository.save(pistachios);
        //productRepository.save(chocolate);
        group.getProducts().add(pistachios);
        group.getProducts().add(chocolate);
        groupRepository.save(group);
        cart.getProducts().add(pistachios);

        //When
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        Assertions.assertEquals("Chocolate", retrievedCart.get().getProducts().get(1).getName());
        Assertions.assertTrue(retrievedCart.get().getProducts().get(1).isActive());
    }

    @Test
    void removeProductFromCartTest() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        Product pistachios = new Product(null, "Pistachios", "200g bag", BigDecimal.valueOf(38.99), new Group(), carts, new ArrayList<>(), true);
        Product chocolate = new Product(null, "Chocolate", "80g", BigDecimal.valueOf(4.99), new Group(), carts, new ArrayList<>(), true);
        Group group = new Group(null, "food", "things to eat", new ArrayList<>(), true);
        pistachios.setGroup(group);
        chocolate.setGroup(group);
        //productRepository.save(pistachios);
        //productRepository.save(chocolate);
        group.getProducts().add(pistachios);
        group.getProducts().add(chocolate);
        groupRepository.save(group);
        cart.getProducts().add(pistachios);
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);

        //When
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());
        List<Product> retrievedProducts = retrievedCart.get().getProducts();
        retrievedProducts.remove(pistachios);
        System.out.println(retrievedProducts.size());
        cart.setProducts((retrievedProducts));
        savedCart = cartRepository.save(cart);
        retrievedCart = cartRepository.findById(savedCart.getId());
        System.out.println(retrievedCart.get().getProducts().size());

        //Then
        Assertions.assertEquals(1, retrievedCart.get().getProducts().size());
        Assertions.assertFalse(retrievedCart.get().getProducts().contains(pistachios));
    }

}