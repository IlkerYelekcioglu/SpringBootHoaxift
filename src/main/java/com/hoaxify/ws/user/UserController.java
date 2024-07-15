package com.hoaxify.ws.user;

import com.hoaxify.ws.auth.dto.token.TokenService;
import com.hoaxify.ws.configuration.CurrentUser;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.dto.UserDTO;
import com.hoaxify.ws.user.dto.UserUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
   UserService userService;

  @Autowired
  TokenService tokenService;

  @PostMapping("/api/v1/users")
   GenericMessage createUser(@Valid @RequestBody UserCreate user) {
    userService.save(user.toUser());
    String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
    return new GenericMessage(message);
  }
  @PatchMapping("api/v1/users/{token}/active")
  GenericMessage activateUser(@PathVariable String token) {
    userService.activateUser(token);
    String message = Messages.getMessageForLocale("hoaxify.activate.user.success.message", LocaleContextHolder.getLocale());
    return new GenericMessage(message);
  }

  @GetMapping("/api/v1/users")
  Page<UserDTO> getUsers(Pageable page,@AuthenticationPrincipal CurrentUser currentUser) {
    return  userService.getUsers(page,currentUser).map(UserDTO::new);
  }
@GetMapping("/api/v1/users/{id}")
UserDTO getUserById(@PathVariable long id) {
    return new UserDTO(userService.getUser(id));
}
@PutMapping("/api/v1/users/{id}")
@PreAuthorize("#id == #principal.id")
  UserDTO updateUser(@PathVariable long id,@RequestBody UserUpdate userUpdate){
  return new UserDTO( userService.updateUser(id,userUpdate));
}

}
