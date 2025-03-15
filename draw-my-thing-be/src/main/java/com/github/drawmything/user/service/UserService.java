package com.github.drawmything.user.service;

import com.github.drawmything.user.model.User;
import com.github.drawmything.user.model.request.UserCreateRequest;
import java.util.Optional;

public interface UserService {

  User create(UserCreateRequest request);

  Optional<User> getByUsername(String username);
}
