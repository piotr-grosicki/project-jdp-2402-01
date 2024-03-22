package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
public class ProductTestSuite {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    GroupRepository groupRepository;
    @AfterEach
    public void cleanup() {
        productRepository.deleteAll();
        groupRepository.deleteAll();
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
    }
    @Test
    public void testFindProductById() {
    // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setActive(true);
         productRepository.save(product);

    // When
        Product retrievedProduct = productRepository.findById(product.getId()).orElse(null);

    // Then
        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
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
        productRepository.save(product);

        // When
        productRepository.delete(product);

        // Then
        Optional<Product> deletedProductOptional = productRepository.findById(product.getId());
        Assertions.assertTrue(deletedProductOptional.isPresent());
        Assertions.assertFalse(deletedProductOptional.get().isActive());
    }
    @Test
    public void testUpdateProduct() {
    // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setActive(true);
        productRepository.save(product);
        Product savedProduct = productRepository.findById(product.getId()).get();

    // When
        savedProduct.setName("Updated Product Name");
        savedProduct.setDescription("Updated Description");
        savedProduct.setPrice(BigDecimal.valueOf(20.00));
        savedProduct.setActive(true);
        Product updatedProduct = productRepository.findById(savedProduct.getId()).get();

    // Then
        assertNotNull(updatedProduct);
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product Name", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(20.00), updatedProduct.getPrice());
        assertTrue(updatedProduct.isActive());

}
    @Test
    public void testFindAllActiveProducts() {
    // Given
        Product product1 = new Product();
        product1.setName("Active Product 1");
        product1.setDescription("Test Description 1");
        product1.setPrice(BigDecimal.valueOf(15.00));
        product1.setActive(true);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Inactive Product");
        product2.setDescription("Test Description 2");
        product2.setPrice(BigDecimal.valueOf(20.00));
        product2.setActive(false);
        productRepository.save(product2);

    // When
        List<Product> activeProducts = productRepository.findAllByActiveTrue();

    // Then
        assertEquals(1, activeProducts.size());
        assertEquals("Active Product 1", activeProducts.get(0).getName());
        assertEquals(2, productRepository.findAll().size());
        assertFalse(activeProducts.contains(product2));
        assertTrue(activeProducts.contains(product1));
    }


}