package com.github.drawmything.config;

import static org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  @Bean
  public AuthorizationManager<Message<?>> messageAuthorizationManager(Builder messages) {
    return messages
        .nullDestMatcher()
        .authenticated()
        .simpDestMatchers("/app/**")
        .fullyAuthenticated()
        .build();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }
}
