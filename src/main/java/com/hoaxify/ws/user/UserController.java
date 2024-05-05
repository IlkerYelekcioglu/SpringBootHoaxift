package com.hoaxify.ws.user;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreateDTO;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.validation.Valid;
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
   GenericMessage createUser(@Valid @RequestBody UserCreateDTO user) {
    userService.save(user.toUser());
    String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
    return new GenericMessage(message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users");
    String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
    apiError.setMessage(message);
    apiError.setStatus(400);
    //Map<String, String> validationErrors = new HashMap<>();
    //for (var fieldError : exception.getBindingResult().getFieldErrors()) {
     // validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    //}
    var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(
        Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage,(existing,replacing)-> existing));
    apiError.setValidationErrors(validationErrors);
    return ResponseEntity.status(400).body(apiError);
  }
  @ExceptionHandler(NotUniqueEmailException.class)
  ResponseEntity<ApiError>  handleNotUniqueEmailEx(NotUniqueEmailException exception) {
    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users");
    apiError.setMessage(exception.getMessage());
    apiError.setStatus(400);
    apiError.setValidationErrors(exception.getValidationError());
    return ResponseEntity.status(400).body(apiError);
  }
  @ExceptionHandler(ActivationNotificationException.class)
  ResponseEntity<ApiError>  handleActivationNotificationException(ActivationNotificationException exception) {
    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users");
    apiError.setMessage(exception.getMessage());
    apiError.setStatus(502);
    return ResponseEntity.status(502).body(apiError);
  }
}
