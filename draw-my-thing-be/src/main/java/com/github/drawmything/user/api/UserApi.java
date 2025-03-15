package com.github.drawmything.user.api;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.github.drawmything.user.model.dto.UserModel;
import com.github.drawmything.user.model.request.UserCreateRequest;
import com.github.drawmything.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserApi {

  UserService userService;

  @ResponseStatus(CREATED)
  @PostMapping
  public UserModel create(@Valid @RequestBody UserCreateRequest request) {
    return UserAssembler.toDto(userService.create(request));
  }

  @ResponseStatus(OK)
  @GetMapping("/exists/username")
  public boolean isUsernameTaken(@RequestBody String username) {
    return userService.isUsernameTaken(username);
  }
}
