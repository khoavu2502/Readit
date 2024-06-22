package com.dev.backend.validator.password;

import com.dev.backend.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    @Override
    public void initialize(PasswordMatches passwordMatches) {
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        String passwordConfirmation = userDto.getPasswordConfirmation();
        return passwordConfirmation.equals(userDto.getPassword());
    }
}
