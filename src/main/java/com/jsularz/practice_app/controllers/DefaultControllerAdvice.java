package com.jsularz.practice_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public final class DefaultControllerAdvice {
    private static final String ERROR_MESSAGE_FORMATTER = " - ";

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<String> handleValidationException(final BindException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(errorMessageFormatter)
                .collect(Collectors.toList());
    }

    private final Function<FieldError, String> errorMessageFormatter =
            error -> error.getField() + ERROR_MESSAGE_FORMATTER + error.getDefaultMessage();
}
