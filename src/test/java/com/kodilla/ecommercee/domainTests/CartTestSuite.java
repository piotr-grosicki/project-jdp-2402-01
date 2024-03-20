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
import java.util.ArrayList;
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

    @Autowired
    private OrderRepository orderRepository;

    User user = new User(null, "Someone", "password123", false, "blahblah", new ArrayList<>(), new ArrayList<>(), true);

    @AfterEach
    public void CleanUp() {
        cartRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testCreateCart() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);

        //When
        Cart savedCart = cartRepository.save(cart);

        //Then
        assertNotNull(savedCart.getId());
        assertTrue(savedCart.isActive());
    }

    @Test
    public void testDeleteCart() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        Cart savedCart = cartRepository.save(cart);

        //When
        cartRepository.delete(cart);

        //Then
        assertNotNull(savedCart.getId());
        assertFalse(savedCart.isActive());
    }

    @Test
    public void testGetCartById() {
        //Given
        userRepository.save(user);
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
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
        Cart cart = new Cart(null, user, new ArrayList<>(), true);
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        Product pistachios = new Product(null, "Pistachios", "200g bag", BigDecimal.valueOf(38.99), new Group(), carts, new ArrayList<>(), true);
        Product chocolate = new Product(null, "Chocolate", "80g", BigDecimal.valueOf(4.99), new Group(), carts, new ArrayList<>(), true);
        Group group = new Group(null, "food", "things to eat", new ArrayList<>(), true);
        pistachios.setGroup(group);
        chocolate.setGroup(group);
        productRepository.save(pistachios);
        productRepository.save(chocolate);
        group.getProducts().add(pistachios);
        group.getProducts().add(chocolate);
        groupRepository.save(group);
        cart.getProducts().add(pistachios);

        //When
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertTrue(retrievedCart.isPresent());
        assertEquals("Chocolate", retrievedCart.get().getProducts().get(1).getName());
        assertTrue(retrievedCart.get().getProducts().get(1).isActive());
    }

    @Test
    public void testRemoveProductFromCart() {
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
        productRepository.save(pistachios);
        productRepository.save(chocolate);
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
        cart.setProducts((retrievedProducts));
        savedCart = cartRepository.save(cart);
        retrievedCart = cartRepository.findById(savedCart.getId());
        System.out.println(retrievedCart.get().getProducts().size());

        //Then
        assertEquals(1, retrievedCart.get().getProducts().size());
        assertFalse(retrievedCart.get().getProducts().contains(pistachios));
    }

    @Test
    public void testCreateOrderFromCart() {
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
        productRepository.save(pistachios);
        productRepository.save(chocolate);
        group.getProducts().add(pistachios);
        group.getProducts().add(chocolate);
        groupRepository.save(group);
        cart.getProducts().add(pistachios);
        cart.getProducts().add(chocolate);
        Cart savedCart = cartRepository.save(cart);

        //When
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());
        List<Product> retrievedProducts = retrievedCart.get().getProducts();
        Order createdOrder = new Order(null, "666",user, retrievedProducts, true);
        user.getOrders().add(createdOrder);
        userRepository.save(user);
        Order savedOrder = orderRepository.save(createdOrder);
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());
        cartRepository.deleteById(savedCart.getId());
        retrievedCart = cartRepository.findById(savedCart.getId());

        //Then
        assertEquals(2, retrievedOrder.get().getProducts().size());
        assertEquals("Pistachios", retrievedOrder.get().getProducts().get(0).getName());
        assertEquals(Optional.empty(), retrievedCart);
    }

}


