package com.jsularz.practice_app.exceptions;

public final class TokenNotFoundException extends RuntimeException {
    private String message;
    public TokenNotFoundException(final String message) {
        super(message);
    }
}
