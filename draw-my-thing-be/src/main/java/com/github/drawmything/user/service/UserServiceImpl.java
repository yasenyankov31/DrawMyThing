package com.github.drawmything.user.service;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.user.model.User;
import com.github.drawmything.user.model.UserRepository;
import com.github.drawmything.user.model.request.UserCreateRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  @Override
  public User create(UserCreateRequest request) {
    if (isUsernameTaken(request.username())) {
      throw new IllegalArgumentException("Used username is taken: " + request.username());
    }

    var encodedPass = passwordEncoder.encode(request.password());
    var user = User.builder().username(request.username()).password(encodedPass).build();
    return userRepository.save(user);
  }

  @Override
  public boolean isUsernameTaken(String username) {
    return Optional.ofNullable(username).flatMap(userRepository::findByUsername).isPresent();
  }

  @Override
  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
