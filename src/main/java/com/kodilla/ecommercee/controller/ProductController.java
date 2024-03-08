package com.kodilla.ecommercee.controller;


import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/product")
public class ProductController {

    @GetMapping
    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping(value = "{productId}")
    public ProductDto getProduct(@PathVariable int productId) {
        return new ProductDto(1, "Pistachios", "200g bag", 38.99, 8);
    }

    @DeleteMapping
    public void deleteProduct(int productId) {

    }

    @PutMapping
    public ProductDto  updateProduct(ProductDto  productDto) {
        return new ProductDto(1, "Peanut Butter", "900g", 24.99, 7);
    }

    @PostMapping
    public void createProduct(ProductDto  productDto) {

    }
}
