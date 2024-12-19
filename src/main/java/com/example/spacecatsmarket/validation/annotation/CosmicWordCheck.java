package com.example.spacecatsmarket.validation.annotation;

import com.example.spacecatsmarket.validation.CosmicWordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CosmicWordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {

    String message() default "The field must contain at least one cosmic term (e.g., 'star', 'galaxy', 'comet')";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
