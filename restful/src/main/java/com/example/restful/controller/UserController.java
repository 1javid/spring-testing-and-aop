package com.example.restful.controller;

import com.example.restful.exceptions.ValidationException;
import com.example.restful.model.dto.UserDto;
import com.example.restful.model.entity.User;
import com.example.restful.model.mapper.UserMapper;
import com.example.restful.service.UserService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UserDto> listAllUsers() {
        return UserMapper.INSTANCE.toUserDtoList(userService.listUsers());
    }

    @SneakyThrows
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable Long userId) {

        Optional<User> res = userService.getUserById(userId);
        Optional<UserDto> resDto = res.map(UserMapper.INSTANCE::toUserDto);

        return resDto.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED) //201
    public UserDto createUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        validationErrorMessage(bindingResult);

        User addUser = userService.saveUser(user);

        return UserMapper.INSTANCE.toUserDto(addUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody User user, BindingResult bindingResult) {

        validationErrorMessage(bindingResult);

        Optional<User> res = userService.updateUser(userId, user);
        Optional<UserDto> resDto = res.map(UserMapper.INSTANCE::toUserDto);

        return resDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable Long userId, @RequestBody Map<String, Object> params) {

        validateParams(params);

        Optional<User> res = userService.partialUpdateUser(userId, params);
        Optional<UserDto> resDto = res.map(UserMapper.INSTANCE::toUserDto);

        return resDto.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    public static void validationErrorMessage(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage()).append("\n");
            }
            String errorMessage = replaceNewlines(sb.toString());

            throw new ValidationException(errorMessage);
        }
    }

    public static String replaceNewlines(String input) {
        String[] words = input.split("\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            boolean isLastWord = (i == words.length - 1);
            sb.append((word.isEmpty()) ? "" : ((i > 0) ? "; " : "") + word + (isLastWord ? "." : ""));
        }

        return sb.toString();
    }

    public void validateParams(Map<String, Object> params) {
        List<String> validationErrors = new ArrayList<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("firstName".equalsIgnoreCase(key)) {
                String firstName = (String) value;
                if (!firstName.matches("[a-zA-Z]+")) {
                    System.out.println("a");
                    validationErrors.add("First name must be a string of letter characters");
                }
                if (firstName == null) {
                    System.out.println("b");
                    validationErrors.add("First name can't be null");
                }
                if (firstName.isEmpty()) {
                    System.out.println("c");
                    validationErrors.add("First name can't be empty");
                }
            }
            if ("lastName".equalsIgnoreCase(key)) {
                String lastName = (String) value;
                if (!lastName.matches("[a-zA-Z]+")) {
                    System.out.println("d");
                    validationErrors.add("Last name must be a string of letter characters");
                }
                if (lastName == null) {
                    System.out.println("e");
                    validationErrors.add("Last name can't be null");
                }
                if (lastName.isEmpty()) {
                    System.out.println("f");
                    validationErrors.add("Last name can't be empty");
                }
            }
            if ("age".equalsIgnoreCase(key)) {
                Object ageValue = params.get("age");
                if (ageValue != null) {
                    if (ageValue instanceof Integer || ageValue instanceof String && isNumeric((String) ageValue)) {
                        if (Integer.parseInt(String.valueOf(ageValue)) < 15) {
                            validationErrors.add("Age must be at least 15");
                        }
                    }
                }
                if(ageValue == null) {
                    System.out.println("h");
                    validationErrors.add("Age can't be null");
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String error : validationErrors) {
            sb.append(error).append("\n");
        }

        if (!validationErrors.isEmpty()) {
            String errorMessage = replaceNewlines(sb.toString());
            throw new ValidationException(errorMessage);
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
}