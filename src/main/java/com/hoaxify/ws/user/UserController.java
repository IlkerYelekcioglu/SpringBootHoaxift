package com.hoaxify.ws.user;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
   UserService userService;

  @PostMapping("/api/v1/users")
   GenericMessage createUser(@Valid @RequestBody User user) {
    System.err.println("------" + LocaleContextHolder.getLocale().getLanguage());
    userService.save(user);
    return new GenericMessage("User is created");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ApiError handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users");
    apiError.setMessage("Validation error");
    apiError.setStatus(400);
    //Map<String, String> validationErrors = new HashMap<>();
    //for (var fieldError : exception.getBindingResult().getFieldErrors()) {
     // validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    //}
    var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(
        Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage,(existing,replacing)-> existing));
    apiError.setValidationErrors(validationErrors);
    return apiError;
  }
  @ExceptionHandler(NotUniqueEmailException.class)
  ApiError  handleNotUniqueEmailEx(NotUniqueEmailException exception) {
    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users");
    apiError.setMessage("Validation error");
    apiError.setStatus(400);
    Map<String,String> validationErrors = new HashMap<>();
    validationErrors.put("email","E-mail in use");
    apiError.setValidationErrors(validationErrors);
    return apiError;
  }
}
