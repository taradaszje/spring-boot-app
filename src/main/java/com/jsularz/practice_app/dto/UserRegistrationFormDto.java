package com.jsularz.practice_app.dto;

import com.jsularz.practice_app.validators.PasswordMatches;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@PasswordMatches
public class UserRegistrationFormDto {

    private Long id;

    @NotNull(message = "Can't be null")
    @NotEmpty(message = "Can't be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotNull(message = "Can't be null")
    @Size(min = 5, message = "Password must have min 5 characters")
    private char[] password;
    private char[] matchingPassword;

    @Email(message = "Incorrect mail")
    @NotNull(message = "Can't be null")
    @NotEmpty(message = "Mail is compulsory")
    private String email;
}
