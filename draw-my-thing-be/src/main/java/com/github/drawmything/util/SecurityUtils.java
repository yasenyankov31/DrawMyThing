package com.github.drawmything.util;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

@NoArgsConstructor(access = PRIVATE)
public class SecurityUtils {

  public static String getCurrentUsername() {
    return getAuthentication().map(Authentication::getName).orElse("anonymous");
  }

  private static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated);
  }
}
