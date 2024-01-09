package com.startsteps.ecommerceapi.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return   Pattern.compile(PASSWORD_PATTERN)
                .matcher(s)
                .matches();
    }
}
