package com.shotty.shotty.domain.post.custom_annotation.validator;

import com.shotty.shotty.domain.post.custom_annotation.annotation.AfterNow;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AfterNowValidator implements ConstraintValidator<AfterNow, LocalDate> {
    @Override
    public void initialize(AfterNow constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate today = LocalDate.now();

        return date.isAfter(today) || date.isEqual(today);
    }
}
