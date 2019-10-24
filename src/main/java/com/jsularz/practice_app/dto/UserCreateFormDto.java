package com.jsularz.practice_app.dto;

import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.validators.PasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@PasswordMatches
@Getter
@Setter
public class UserCreateFormDto {

    private Long id;

    @NotBlank(message = "Can't be blank")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotNull(message = "Can't be null")
    @Size(min = 5, message = "Password must have min 5 characters")
    private char[] password;
    private char[] matchingPassword;

    @Email(message = "Incorrect mail")
    @NotEmpty(message = "Mail is compulsory")
    private String email;

    public UserCreateFormDto(final User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
    }
}
