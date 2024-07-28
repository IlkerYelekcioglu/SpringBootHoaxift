package com.hoaxify.ws.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Token {

  @Id
  String token;

  @Transient
  String prefix = "Bearer";

  /*
  bir entity içerisinde oluşturulan bir alanın db de gözükmemesini istiyorsak @Transient annotation nunu kullanırız
   */
  @JsonIgnore
  @ManyToOne
  User user;

  public Token() {
  }

  public Token(String prefix, String token) {
    this.prefix = prefix;
    this.token = token;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
