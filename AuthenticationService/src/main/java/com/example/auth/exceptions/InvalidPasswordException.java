package com.example.auth.exceptions;

import java.io.Serial;

public class InvalidPasswordException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 53457089789182737L;
    public InvalidPasswordException(final String message) {
        super( message);
    }
}
