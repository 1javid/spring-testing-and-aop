package com.example.restful.service;

import com.example.restful.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> listUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    void deleteUserById(Long id);
    Optional<User> updateUser(Long id, User user);
    Optional<User> partialUpdateUser(Long id, Map<String, Object> p);
}
