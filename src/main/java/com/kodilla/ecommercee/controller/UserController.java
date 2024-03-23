package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private Map<Long, User> users = new HashMap<>();
    private Random random = new Random();

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            User user = userMapper.mapToUser(userDto);
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    @PostMapping("/users/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Long userId){
        try {
            userService.blockUser(userId);
            return ResponseEntity.ok("User blocked successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to block user");
        }
    }

    @PostMapping("/generateApiKey/{userId}")
    public ResponseEntity<String> generateApiKey(@PathVariable Long userId) {
        try {
            String apiKey = userService.generateApiKey(userId);
            return ResponseEntity.ok("API key generated successfully: " + apiKey);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate API key");
        }
    }

}
