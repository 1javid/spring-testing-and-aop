package com.example.restful;

import com.example.restful.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<User, Long> {

}
