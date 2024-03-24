package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(final Long userId) throws UserNotFoundException{
        userRepository.delete(userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(UserNotFoundException::new));
    }

    public void blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.setBlocked(true);
        userRepository.save(user);
    }

    public String generateApiKey(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        // Sprawdzam czy klucz został wygenerowany w ciągu ostatniej godziny
        if (user.getApiKey() != null && user.getApiKeyExpiration() != null
                && LocalDateTime.now().isBefore(user.getApiKeyExpiration())) {
            return "API key has already been generated and is still valid.";
        }
        String newApiKey = UUID.randomUUID().toString();
        // Ustawiam ważność klucza na godzinę
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
        user.setApiKey(newApiKey);
        user.setApiKeyExpiration(expiration);
        userRepository.save(user);
        return "API key generated successfully and will expire in 1 hour.";
    }
}
