package com.jsularz.practice_app.dto;

import com.jsularz.practice_app.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateFormDto {

    private Long id;

    @NotNull(message = "Can't be null")
    @NotEmpty(message = "Can't be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    private char[] password;

    @Email(message = "Incorrect mail")
    @NotNull(message = "Can't be null")
    @NotEmpty(message = "Mail is compulsory")
    private String email;

    private Set<Role> roles = new HashSet<>();
}
