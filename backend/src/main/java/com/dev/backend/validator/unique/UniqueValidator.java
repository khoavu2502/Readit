package com.dev.backend.validator.unique;

import com.dev.backend.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private UserRepository userRepository;
    private String fieldName;

    @Autowired
    public UniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(Unique unique) {
        this.fieldName = unique.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (fieldName) {
            case "username" -> !userRepository.existsByUsername(value);
            case "email" -> !userRepository.existsByEmail(value);
            default -> false;
        };
    }
}
