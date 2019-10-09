package com.jsularz.practice_app.exceptions;

public final class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message){
        super(message);
    }
}
