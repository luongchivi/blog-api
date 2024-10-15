package com.luongchivi.blog_api.validator.handle;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.luongchivi.blog_api.validator.MinNumberConstraint;

public class MinNumberValidator implements ConstraintValidator<MinNumberConstraint, Integer> {
    private int min;

    @Override
    public void initialize(MinNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value > min;
    }
}
