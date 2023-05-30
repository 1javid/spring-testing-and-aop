package com.example.restful.service.impl;

import com.example.restful.model.entity.User;
import com.example.restful.repository.UserRepository;
import com.example.restful.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        Optional<User> older = userRepository.findById(id);
        if (older.isEmpty()) return Optional.empty();
        user.setId(id);
        user.setCreatedAt(older.get().getCreatedAt());
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> partialUpdateUser(Long id, Map<String, Object> params) {
        Optional<User> res = userRepository.findById(id);

        if(res.isEmpty()) return res;

        User old = res.get();

        params.forEach((k, v) -> {
            try {
                Field field = ReflectionUtils.findField(User.class, k);
                field.setAccessible(true);
                if (v instanceof String && isNumeric((String) v)) {
                    Integer numericValue = Integer.parseInt((String) v);
                    field.set(old, numericValue);
                } else {
                    field.set(old, v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return Optional.of(userRepository.save(old));
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
}
