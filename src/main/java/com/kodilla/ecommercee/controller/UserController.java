package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Long, User> users = new HashMap<>();
    private Random random = new Random();

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        Long userId = Long.valueOf(users.size()) + 1;
        String apiKey = generateRandomApiKey();
        User user = new User(userId, userDto.getUsername(), userDto.getPassword(), false,
                apiKey, new ArrayList<>(), new ArrayList<>(), true);
        users.put(userId, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/users/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Long userId){
        User user = users.get(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        user.setBlocked(true);
        return ResponseEntity.ok("User blocked successfully");
    }

    private String generateRandomApiKey() {
        return UUID.randomUUID().toString();
    }
}
