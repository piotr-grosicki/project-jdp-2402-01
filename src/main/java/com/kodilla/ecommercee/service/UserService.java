package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user){
        return userRepository.save(user);
    }
   public User getUserById(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
   }
   public List<User> getAllUsers(){
        return userRepository.findAll();
   }
   public void deleteUser(final Long userId) throws UserNotFoundException{
        userRepository.delete(userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(UserNotFoundException::new));
   }

}
