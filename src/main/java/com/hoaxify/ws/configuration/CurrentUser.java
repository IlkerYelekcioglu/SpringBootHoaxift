package com.hoaxify.ws.configuration;

import com.hoaxify.ws.user.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CurrentUser implements UserDetails {

  long id;

  String username;

  String password;

  boolean enabled;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public CurrentUser(User user) {
    this.id = user.getId();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.enabled = user.isActive();
  }



  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList("ROLE_USER");
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
