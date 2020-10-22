package com.richmond.cookbook.service;


import com.richmond.cookbook.entity.Recipe;
import com.richmond.cookbook.entity.Role;
import com.richmond.cookbook.entity.User;
import com.richmond.cookbook.repository.RoleRepository;
import com.richmond.cookbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    public User saveNewUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(role);
        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteRecipe(User user, Recipe recipe) {
        user.removeRecipe(recipe);
        userRepository.save(user);
    }
}
