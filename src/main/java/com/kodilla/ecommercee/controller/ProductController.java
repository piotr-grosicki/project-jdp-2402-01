package com.kodilla.ecommercee.controller;


import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        return new ProductDto(1, "Pistachios", "200g bag",  BigDecimal.valueOf(38.99), 8);
    }

    @DeleteMapping(value = "{productId}")
    public void deleteProduct(@PathVariable int productId) {

    }

    @PutMapping
    public ProductDto  updateProduct(@RequestBody ProductDto  productDto) {
        return new ProductDto(1, "Peanut Butter", "900g",  BigDecimal.valueOf(24.99), 7);
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto  productDto) {

    }
}
