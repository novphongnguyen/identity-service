package com.spring.identity_service.services;

import com.spring.identity_service.DTOs.UserCreateRequest;
import com.spring.identity_service.DTOs.UserUpdateRequest;
import com.spring.identity_service.entities.User;
import com.spring.identity_service.exceptions.AppException;
import com.spring.identity_service.exceptions.ErrorCode;
import com.spring.identity_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(UserCreateRequest request) {
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public User updateUser (String id, UserUpdateRequest request) {
        User user = getUserById(id);
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        return userRepository.save(user);
    }

    public String deleteUser(String id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
        return "deleted";
    }
}
