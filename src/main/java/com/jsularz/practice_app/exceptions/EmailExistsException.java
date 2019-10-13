package com.jsularz.practice_app.exceptions;

public final class EmailExistsException extends RuntimeException {
    public EmailExistsException(final String message){
        super(message);
    }
}
