package com.jsularz.practice_app;

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
        user.setUsername(randomAlphabetic(8));
        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(random(5).toCharArray());
        user.setEmail(randomAlphabetic(10));
        user.setRoles(Collections.emptySet());
        return user;
    }

}
