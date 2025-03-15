package com.github.drawmything.user.api;

import com.github.drawmything.user.model.User;
import com.github.drawmything.user.model.dto.UserModel;

public class UserAssembler {

  public static UserModel toDto(User user) {
    return UserModel.builder().id(user.getId()).username(user.getUsername()).build();
  }
}
