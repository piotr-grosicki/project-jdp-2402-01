package com.kodilla.ecommercee.exception;
public class CartNotFoundException extends Exception {
    public CartNotFoundException(Long cartId) {
        super("Cart with id: " + cartId + " not found.");
    }
}
