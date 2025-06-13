package com.example.user.exceptions;

import java.io.Serial;

public class DepartmentNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 53457089789182737L;
    public DepartmentNotFoundException(final String message) {
        super( message);
    }
}
