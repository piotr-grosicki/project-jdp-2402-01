package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
public class ProductTestSuite {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    GroupRepository groupRepository;

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
        assertEquals(0, groupRepository.findAll().size());}
    @Test
    public void testFindProductById() {
    // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setActive(true);
        Product savedProduct = productRepository.save(product);

    // When
        Long productId = savedProduct.getId();
        Product retrievedProduct = productRepository.findById(productId).orElse(null);

    // Then
        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals("Test Description", retrievedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(10.00), retrievedProduct.getPrice());
        assertTrue(retrievedProduct.isActive());
    }
    @Test
    public void testDeleteProduct() {
    // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setActive(true);
        Product savedProduct = productRepository.save(product);

    // When
        productRepository.deleteById(savedProduct.getId());

    // Then
        assertNull(productRepository.findById(savedProduct.getId()).orElse(null), "Product should be deleted");
    }
    @Test
    public void testUpdateProduct() {
    // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setActive(true);
        Product savedProduct = productRepository.save(product);

    // When
        savedProduct.setName("Updated Product Name");
        savedProduct.setDescription("Updated Description");
        savedProduct.setPrice(BigDecimal.valueOf(20.00));
        savedProduct.setActive(true); // Change active status to true
        Product updatedProduct = productRepository.save(savedProduct);

    // Then
        assertNotNull(updatedProduct);
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product Name", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(20.00), updatedProduct.getPrice());
        assertTrue(updatedProduct.isActive());

}}