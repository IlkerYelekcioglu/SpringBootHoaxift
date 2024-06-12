package com.hoaxify.ws.auth.dto.token;

import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.user.User;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthTokenService implements TokenService {

  @Override
  public Token createToken(User user, Credentials creds) {

    String emailColonPassword =creds.email() + ":" + creds.password();
    String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
     return  new Token("Basic",token);
  }

  @Override
  public User verifyUser(String authorizationHeader) {
    return null;
  }
}
