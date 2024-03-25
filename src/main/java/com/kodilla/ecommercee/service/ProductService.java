package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllByActiveTrue();
    }

    public Product getProduct(final Long productId) throws ProductNotFoundException {
        return productRepository.findByIdAndActiveTrue(productId).orElseThrow(ProductNotFoundException::new);
    }

    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(final Long productId) throws ProductNotFoundException {
            productRepository.delete(productRepository.findByIdAndActiveTrue(productId).
                    orElseThrow(ProductNotFoundException::new));
    }
}
