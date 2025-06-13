package com.example.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
