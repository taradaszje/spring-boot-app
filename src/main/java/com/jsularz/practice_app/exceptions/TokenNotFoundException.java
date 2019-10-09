package com.jsularz.practice_app.exceptions;

public class TokenNotFoundException extends RuntimeException {
    String message;
    public TokenNotFoundException(String message) {
        super(message);
    }
}
