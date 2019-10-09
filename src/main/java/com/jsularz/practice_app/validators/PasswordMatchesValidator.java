package com.jsularz.practice_app.validators;

import com.jsularz.practice_app.dto.UserCreateFormDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserCreateFormDto user = (UserCreateFormDto) obj;
        return Arrays.equals(user.getPassword(), user.getMatchingPassword());
    }
}
