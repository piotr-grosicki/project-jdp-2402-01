package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductTestSuite {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    GroupRepository groupRepository;
    @Test
    public void testCreateProduct(){
    //Given
        Long productId = 1L;
        String name = "Test product";
        String description = "Test description";
        BigDecimal price = BigDecimal.valueOf(12);
        Group group = new Group(1L, "Test Group", "Test description", new ArrayList<>(),true);
    //When
        Product product = new Product(productId, name, description, price, group, new ArrayList<>(),new ArrayList<>(), true);
    //Then
        assertNotNull(product);
        assertEquals(productId, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(group, product.getGroup());
        assertNotNull(product.getCarts());
        assertNotNull(product.getOrders());
        assertEquals(true, product.isActive());
    }
    @Test
    public  void  testAddProductToCart(){
    //Given
        Long productId = 1L;
        String name = "Test product";
        String description = "Test description";
        BigDecimal price = BigDecimal.valueOf(12);
        Group group = new Group(1L, "Test Group", "Test description", new ArrayList<>(),true);
        Product product = new Product(productId, name, description, price, group, new ArrayList<>(), new ArrayList<>(), true);
        Cart cart = new Cart();
    //When
        product.getCarts().add(cart);
    // Then
        assertTrue(product.getCarts().contains(cart));
        assertEquals(productId, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(group, product.getGroup());
        assertTrue(product.isActive());
    }
    @Test
    public void testAddProductToOrder(){

    // Given
        Long productId = 1L;
        String name = "Test product";
        String description = "Test description";
        BigDecimal price = BigDecimal.valueOf(12);
        Group group = new Group(1L, "Test Group", "Test description", new ArrayList<>(), true);

        Product product = new Product(productId, name, description, price, group, new ArrayList<>(), new ArrayList<>(), true);
        Order order = new Order();
    // When
        product.getOrders().add(order);
    // Then
        assertTrue(product.getOrders().contains(order));
        assertEquals(productId, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(group, product.getGroup());
        assertTrue(product.isActive());
    }
    @Test
    public void testAddProductToDatabase() {
    // Given
        Group group = new Group();
        group.setName("Test group");
        group.setDescription("Test Group Description");
        Group savedGroup = groupRepository.save(group);
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setGroup(savedGroup);
        product.setCarts(Arrays.asList(new Cart()));
        product.setOrders(Arrays.asList(new Order()));
        product.setActive(true);

    //When
     Product savedProduct = productRepository.save(product);
    //Then
        assertNotNull(savedGroup);
        assertEquals("Test Product", savedProduct.getName());
        assertEquals("Test Description", savedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(10.00), savedProduct.getPrice());
        assertNotNull(savedProduct.getGroup());
        assertEquals(1, savedProduct.getCarts().size());
        assertEquals(1, savedProduct.getOrders().size());
        assertTrue(savedProduct.isActive());
    //CleanUp
        productRepository.deleteAll();
        groupRepository.deleteAll();
    // Verify cleanup
        assertEquals(0, productRepository.findAll().size());
        assertEquals(0, groupRepository.findAll().size());}}
