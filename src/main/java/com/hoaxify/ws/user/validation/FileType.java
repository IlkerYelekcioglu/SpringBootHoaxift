package com.hoaxify.ws.user.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileType {
  java.lang.String message() default "Only {types} are allowed";

  java.lang.Class<?>[] groups() default {};

  java.lang.Class<? extends jakarta.validation.Payload>[] payload() default {};

  String[] types();

}
