package com.shotty.shotty.domain.post.custom_annotation.validator;

import com.shotty.shotty.domain.post.custom_annotation.annotation.AfterOrEqualToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AfterOrEqualTodayValidator implements ConstraintValidator<AfterOrEqualToday, LocalDate> {
    @Override
    public void initialize(AfterOrEqualToday constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate today = LocalDate.now();

        return date.isAfter(today) || date.isEqual(today);
    }
}
