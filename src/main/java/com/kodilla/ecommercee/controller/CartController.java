package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartMapper cartMapper;
    private final CartService cartService;
    //private final ProductService productService;



    @GetMapping("/{cartId}")
    public CartDto getCartById(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        return cartMapper.mapToCartDto(cart);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCart(@RequestBody CartDto cartDto) throws CartNotFoundException {
        Cart cart = cartMapper.mapToCart(cartDto);
        cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }
/*
     @PutMapping("/add/{cartId}/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        Product product = productService.getProduct(productId);
        Cart cart = cartService.addProductToCart(cartId, product);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }
*/
    @DeleteMapping("/delete/{cartId}/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        Product product = cartService.getProductFromCart(cartId, productId);
        Cart cart = cartService.deleteProductFromCart(cartId, product);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PostMapping("/order/{cartId}")
    public ResponseEntity<Void> createOrderFromCart(@PathVariable Long cartId) throws CartNotFoundException {
        cartService.createOrderFromCart(cartId);
        return ResponseEntity.ok().build();
    }
}
