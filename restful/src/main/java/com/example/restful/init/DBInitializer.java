package com.example.restful.init;

import com.example.restful.model.entity.User;
import com.example.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer {

    @Bean
    @Autowired
    public CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User("Javid", "Alakbarli", 19));
            userRepository.save(new User("Mushfig", "Jafarov", 18));
            userRepository.save(new User("Ali", "Ahad", 52));
        };
    }
}
