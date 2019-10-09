package com.jsularz.practice_app.exceptions;

public final class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message) {
        super(message);
    }

}
