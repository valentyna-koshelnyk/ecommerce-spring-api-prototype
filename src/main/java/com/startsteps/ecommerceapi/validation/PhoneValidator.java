package com.startsteps.ecommerceapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final String PHONE_PATTERN = "^[0-9]{10}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return   Pattern.compile(PHONE_PATTERN)
                .matcher(value)
                .matches();
    }
}
