package com.shotty.shotty.domain.post.custom_annotation.annotation;

import com.shotty.shotty.domain.post.custom_annotation.validator.AfterNowValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Constraint(validatedBy = AfterNowValidator.class)
@Target({ElementType.FIELD})
public @interface AfterNow {
    String message() default "현재 날짜보다 이후여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
