package com.jsularz.practice_app.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(nullable = false, unique = true)
    private User user;

    private LocalDateTime expiryDate;

    public VerificationToken(final String token, final User user) {
        this.token = token;
        this.setUser(user);
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private LocalDateTime calculateExpiryDate(final long expiryTimeInMinutes) {
        final LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
        return localDateTime;
    }
}
