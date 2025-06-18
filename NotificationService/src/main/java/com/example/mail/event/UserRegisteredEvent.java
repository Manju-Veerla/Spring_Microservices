package com.example.mail.event;

public record UserRegisteredEvent(
        String username,
    String firstName,
    String lastName,
    String email,
    String departmentName
) {
    public UserRegisteredEvent {
        if (firstName == null || lastName == null || email == null || departmentName == null) {
            throw new IllegalArgumentException("All fields must be provided");
        }
    }
}
