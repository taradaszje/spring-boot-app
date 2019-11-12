package com.jsularz.practice_app;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;
import com.jsularz.practice_app.models.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestObjectFactory {

    public static User createUser(){
        final User user = new User();
        user.setLastLogin(LocalDateTime.now());
        user.setVerificationToken(null);
        user.setStatus(new Random().nextBoolean());
        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(random(5).toCharArray());
        user.setEmail(randomAlphabetic(10));
        user.setRoles(Collections.emptySet());
        return user;
    }

    public static Role createRole(final RoleType roleType){
        return new Role(roleType);
    }

    public static UserCreateFormDto createUserCreateForm() {
        final UserCreateFormDto userCreateFormDto = new UserCreateFormDto();
        userCreateFormDto.setEmail(randomAlphabetic(7) + "@"+"gmail.com");
        userCreateFormDto.setPassword(randomAlphabetic(10).toCharArray());
        userCreateFormDto.setMatchingPassword(userCreateFormDto.getPassword());
        return userCreateFormDto;
    }

    public static User createUser(final UserCreateFormDto form){
        final User user = new User();
        user.setLastLogin(LocalDateTime.now());
        user.setVerificationToken(null);
        user.setStatus(new Random().nextBoolean());
        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(random(5).toCharArray());
        user.setEmail(randomAlphabetic(10));
        user.setRoles(Collections.emptySet());
        return user;
    }
}
