package com.example.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.model.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByDepartmentId(Long departmentId);
}
