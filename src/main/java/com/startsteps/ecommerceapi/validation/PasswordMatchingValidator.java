package com.startsteps.ecommerceapi.validation;

import com.startsteps.ecommerceapi.payload.request.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, SignupRequest> {
    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(SignupRequest signupRequest, ConstraintValidatorContext constraintValidatorContext) {
        return signupRequest.getPassword().equals(signupRequest.getMatchingPassword());
    }
}