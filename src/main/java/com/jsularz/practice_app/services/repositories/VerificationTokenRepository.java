package com.jsularz.practice_app.services.repositories;

import com.jsularz.practice_app.exceptions.TokenNotFoundException;
import com.jsularz.practice_app.models.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token) throws TokenNotFoundException;

}
