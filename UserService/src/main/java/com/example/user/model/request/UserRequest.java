package com.example.user.model.request;

public record UserRequest(Long id, String firstName, String username, String password, String lastName, String email, Long departmentId) {}
