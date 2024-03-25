package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
    public void cleanUp() {
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    Group group = new Group("Test Group", "Test Description");
    Product product = new Product("Test Product", "Test Description", BigDecimal.valueOf(10.00), group);

    @Test
    public void testAddProductToDatabase() {
        //Given
        Group savedGroup = groupRepository.save(group);
        Product savedProduct = productRepository.save(product);
        product.setCarts(List.of(new Cart()));
        product.setOrders(List.of(new Order()));

        //When & Then
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
        //Given
        groupRepository.save(group);
        productRepository.save(product);

        //When
        Product retrievedProduct = productRepository.findById(product.getId()).orElse(null);

        //Then
        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals("Test Description", retrievedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(10.00), retrievedProduct.getPrice());
        assertTrue(retrievedProduct.isActive());
    }

    @Test
    public void testDeleteProduct() {
        //Given
        groupRepository.save(group);
        productRepository.save(product);

        //When
        productRepository.delete(product);
        Optional<Product> deletedProductOptional = productRepository.findById(product.getId());

        //Then
        assertTrue(deletedProductOptional.isPresent());
        assertFalse(deletedProductOptional.get().isActive());
    }

    @Test
    public void testUpdateProduct() {
        //Given
        groupRepository.save(group);
        productRepository.save(product);
        Product savedProduct = productRepository.findById(product.getId()).get();

        //When
        savedProduct.setName("Updated Product Name");
        savedProduct.setDescription("Updated Description");
        savedProduct.setPrice(BigDecimal.valueOf(20.00));
        Product updatedProduct = productRepository.findById(savedProduct.getId()).get();

        //Then
        assertNotNull(updatedProduct);
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product Name", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(20.00), updatedProduct.getPrice());
        assertTrue(updatedProduct.isActive());
}

    @Test
    public void testFindAllActiveProducts() {
        //Given
        groupRepository.save(group);
        Product product1 = new Product("Active Product 1","Test Description 1",BigDecimal.valueOf(15.00),group);
        productRepository.save(product1);

        Product product2 = new Product("Inactive Product","Test Description 2",BigDecimal.valueOf(20.00), group);
        product2.setActive(false);
        productRepository.save(product2);

        //When
        List<Product> activeProducts = productRepository.findAllByActiveTrue();

        //Then
        assertEquals(1, activeProducts.size());
        assertEquals(product1.getName(), activeProducts.get(0).getName());
        assertEquals(2, productRepository.findAll().size());
        assertTrue(activeProducts.contains(product1));
        assertFalse(activeProducts.contains(product2));
    }
}
