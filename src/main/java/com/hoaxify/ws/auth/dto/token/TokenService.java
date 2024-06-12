package com.hoaxify.ws.auth.dto.token;


import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.user.User;

public interface TokenService {
    public Token createToken(User user, Credentials creds);

    public User verifyUser(String authorizationHeader);
}
