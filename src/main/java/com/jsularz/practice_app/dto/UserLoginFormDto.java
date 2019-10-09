package com.jsularz.practice_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserLoginFormDto {

    @Email(message = "Incorrect mail")
    @NotNull(message = "Can't be null")
    @NotEmpty(message = "Mail is compulsory")
    private String email;

    @NotNull(message = "Can't be null")
    @Size(min = 5, message = "Password must have min 5 characters")
    private char[] password;
}