package com.luongchivi.blog_api.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.luongchivi.blog_api.validator.handle.DateOfBirthValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DateOfBirthValidator.class})
public @interface DateOfBirthConstraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
