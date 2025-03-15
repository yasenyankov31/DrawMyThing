package com.github.drawmything.user.service;

import com.github.drawmything.user.model.User;
import java.util.Optional;

public interface UserService {

  Optional<User> getByUsername(String username);
}
