package com.luongchivi.blog_api.validator.handle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.luongchivi.blog_api.validator.DateOfBirthConstraint;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirthConstraint, LocalDate> {
    private int min;

    @Override
    public void initialize(DateOfBirthConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min;
    }
}
