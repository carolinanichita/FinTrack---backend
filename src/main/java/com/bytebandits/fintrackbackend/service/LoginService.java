package com.bytebandits.fintrackbackend.service;

import com.bytebandits.fintrackbackend.model.User;
import com.bytebandits.fintrackbackend.repository.UserRepository;
import com.bytebandits.fintrackbackend.util.RandomPasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoginService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword);
        userRepository.save(user);
        return user;
    }

    private String generateRandomPassword() {
        return RandomPasswordGenerator.generateRandomPassword();
    }

    public Boolean validatePassword() {
        return false;
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
