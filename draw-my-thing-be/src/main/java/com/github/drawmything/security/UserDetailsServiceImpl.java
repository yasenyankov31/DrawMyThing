package com.github.drawmything.security;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService
        .getByUsername(username)
        .map(UserDetailsServiceImpl::toSpringUser)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  private static User toSpringUser(com.github.drawmything.user.model.User user) {
    return new User(user.getUsername(), user.getPassword(), List.of());
  }
}
