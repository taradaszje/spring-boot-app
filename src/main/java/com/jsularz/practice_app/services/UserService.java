package com.jsularz.practice_app.services;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserUpdateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;

public interface UserService {
    User createNewUserAccount(final UserCreateFormDto user) throws EmailExistsException;
    VerificationToken getVerificationToken(final String verificationToken);
    void createVerificationToken(final User user, final String token);
    Iterable<User> findAll();
    User findById(final Long id);
    void updateUser(final Long id, final UserUpdateFormDto updateForm);
    void updateUser(final User user);
    void deleteById(final Long id);
    boolean checkEmailExist(final String email);
    void saveUser(final User user);
    User findByEmail(final String email);
}
// todo move token methods to proper service