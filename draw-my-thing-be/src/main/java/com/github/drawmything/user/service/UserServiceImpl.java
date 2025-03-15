package com.github.drawmything.user.service;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.user.model.User;
import com.github.drawmything.user.model.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
