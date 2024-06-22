package com.dev.backend.dto;

import com.dev.backend.validator.password.PasswordMatches;
import com.dev.backend.validator.unique.Unique;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@PasswordMatches
public class UserDto {

    private Long id;

    @NotBlank(message = "username cannot be empty")
    @Unique(fieldName = "username", message = "username already exists")
    @Size(min = 10, max = 25, message = "username must be between {min} and {max} characters")
    private String username;

    @NotBlank(message = "email cannot be empty")
    @Unique(fieldName = "email", message = "email already exists")
    @Email
    @Size(min = 10, max = 30, message = "email must be between {min} and {max} characters")
    private String email;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 10, max = 50, message = "password must be between {min} and {max} characters")
    private String password;

    @NotBlank(message = "confirm your password")
    private String passwordConfirmation;

    private Date createdAt;

    private String avatar;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<UserDto> followers;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<UserDto> following;
}
