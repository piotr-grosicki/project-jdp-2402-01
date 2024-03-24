package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.FailedToBlockUserException;
import com.kodilla.ecommercee.exception.FailedToCreateUserException;
import com.kodilla.ecommercee.exception.FailedToGenerateApiKeyException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) throws FailedToCreateUserException {
        try {
            User user = userMapper.mapToUser(userDto);
            userService.createUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new FailedToCreateUserException();
        }
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<Void> blockUser(@PathVariable Long userId) throws FailedToBlockUserException {
        try {
            userService.blockUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new FailedToBlockUserException();
        }
    }

    @PostMapping("/generateApiKey/{userId}")
    public ResponseEntity<String> generateApiKey(@PathVariable Long userId) throws FailedToGenerateApiKeyException {
        try {
            String apiKey = userService.generateApiKey(userId);
            return ResponseEntity.ok("API key generated successfully: " + apiKey);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new FailedToGenerateApiKeyException();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
