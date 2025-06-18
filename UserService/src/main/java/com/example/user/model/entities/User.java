package com.example.user.model.entities;

import com.example.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private Long departmentId;
    @Enumerated(EnumType.STRING)
    private Role role;
}
