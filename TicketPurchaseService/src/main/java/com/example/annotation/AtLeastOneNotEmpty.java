package com.example.annotation;

import com.example.validator.AtLeastOneNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNotEmptyValidator.class)
public @interface AtLeastOneNotEmpty {
    String message() default "At least one field should be not null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
