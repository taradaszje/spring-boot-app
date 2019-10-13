package com.jsularz.practice_app.services;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;

public interface UserService {
    User createNewUserAccount(final UserCreateFormDto user) throws EmailExistsException;
    VerificationToken getVerificationToken(final String verificationToken);
    void createVerificationToken(final User user, final String token);
}
