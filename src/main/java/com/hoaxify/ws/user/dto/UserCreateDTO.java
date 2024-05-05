package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
    @Size(min = 4,max = 255)
    @NotBlank(message = "{hoaxify.constraint.username.notblank}")
    String username,

    @NotBlank
    @Email
    @UniqueEmail
    String email,

    @Size(min = 8,max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{hoaxify.constraint.email.pattern}")
    String password
) {
public User toUser(){
  User user = new User();
  user.setEmail(email);
  user.setUsername(username);
  user.setPassword(password);
  return user;
}
}
