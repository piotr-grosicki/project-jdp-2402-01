package com.kodilla.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException exception) {
        return new ResponseEntity<>("Cart with given Id does not exist: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException exception) {
        return new ResponseEntity<>("Group with given Id does not exist: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {
        return new ResponseEntity<>("The order you are looking for does not exist. Error:" + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        return new ResponseEntity<>("Product with given Id does not exist: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given Id does not exist: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailedToCreateUserException.class)
    public ResponseEntity<Object> handleFailedToCreateUserException(FailedToCreateUserException exception) {
        return new ResponseEntity<>("Failed to create user: " + exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToBlockUserException.class)
    public ResponseEntity<Object> handleFailedToBlockUserException(FailedToBlockUserException exception) {
        return new ResponseEntity<>("Failed to block user: " + exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToGenerateApiKeyException.class)
    public ResponseEntity<Object> handleFailedToGenerateApiKeyException(FailedToGenerateApiKeyException exception) {
        return new ResponseEntity<>("Failed to generate API key: " + exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}