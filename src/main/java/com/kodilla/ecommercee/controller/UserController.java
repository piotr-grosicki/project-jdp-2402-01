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
    private Map<Integer, User> users = new HashMap<>();
    private Random random = new Random();

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        int id = users.size()+1;
        String apiKey = generateRandomApiKey();
        User user = new User(id, userDto.getUsername(), userDto.getPassword(),  false, apiKey, new ArrayList<>());
        users.put(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successful user creation");
    }

    @PostMapping("/users/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable int userId){
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
