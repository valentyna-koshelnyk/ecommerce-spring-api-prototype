package com.startsteps.ecommerceapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;

@Documented
@Target({TYPE, FIELD, PARAMETER, RECORD_COMPONENT})
@Constraint(validatedBy = PhoneValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {
    String message() default "Phone should contain just numbers, start from the country code without +" +
            "and contain just 10 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

