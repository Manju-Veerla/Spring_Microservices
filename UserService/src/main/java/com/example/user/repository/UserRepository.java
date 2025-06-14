package com.example.user.repository;

import com.example.user.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByDepartmentId(Long departmentId);

    Optional<User> findByUsername(String username);
}
