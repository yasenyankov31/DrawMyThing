package com.github.drawmything.user.service;

import static com.github.drawmything.util.SecurityUtils.getCurrentUsername;

import com.github.drawmything.user.model.User;
import com.github.drawmything.user.model.request.UserCreateRequest;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

  User create(UserCreateRequest request);

  boolean isUsernameTaken(String username);

  Optional<User> getByUsername(String username);

  default User getCurrentUser() {
    return getByUsername(getCurrentUsername())
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    "User not found with username: " + getCurrentUsername()));
  }
}
