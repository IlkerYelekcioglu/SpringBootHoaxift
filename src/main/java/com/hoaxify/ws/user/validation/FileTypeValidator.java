package com.hoaxify.ws.user.validation;

import com.hoaxify.ws.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FileTypeValidator implements ConstraintValidator<FileType, String>
{
  @Autowired
  FileService fileService;

  String[] types;

  @Override
  public void initialize(FileType constraintAnnotation) {
  this.types =  constraintAnnotation.types();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value == null || value.isEmpty()) return true;
    String type = fileService.detectType(value);
    for(String validTypes : types) {
      if (type.contains(validTypes)) {
        return true;
      }
    }

    String validTypes = Arrays.stream(types).collect(Collectors.joining(", "));
    context.disableDefaultConstraintViolation();
    HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
    hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
    hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
    return false;

  }
}
