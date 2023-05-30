package com.example.restful.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name can't be null")
    @NotBlank(message = "First name can't be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must be a string of letter characters")
    private String firstName;

    @NotNull(message = "Last name can't be null")
    @NotBlank(message = "Last name can't be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must be a string of letter characters")
    private String lastName;

    @NotNull(message = "Age can't be null")
    @Min(value = 15, message = "Age must be at least 15")
    private Integer age;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public User(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}