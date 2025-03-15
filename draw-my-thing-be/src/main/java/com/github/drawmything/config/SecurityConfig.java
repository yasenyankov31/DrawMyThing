package com.github.drawmything.config;

import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  private static final String APIS = "/api/**";

  @Bean
  public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
    return http.securityMatcher(APIS)
        .requestCache(withDefaults())
        .csrf(CsrfConfigurer::disable)
        .cors(CorsConfigurer::disable)
        .authorizeHttpRequests(requests -> requests.requestMatchers(APIS).fullyAuthenticated())
        .build();
  }

  @Bean
  public SecurityFilterChain htmlFilterChain(
      HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
    return http.csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
        .csrf(CsrfConfigurer::disable)
        .cors(CorsConfigurer::disable)
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::deny))
        .requestCache(withDefaults())
        .authorizeHttpRequests(
            requests ->
                requests
                    .dispatcherTypeMatchers(FORWARD)
                    .permitAll()
                    .requestMatchers(permittedHtmlRequests())
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated())
        .logout(
            logout ->
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", GET.name()))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                    .permitAll())
        .formLogin(
            formLogin ->
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/login-do")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error=true"))
        .authenticationProvider(authenticationProvider)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    var authProvider = new DaoAuthenticationProvider(passwordEncoder);
    authProvider.setUserDetailsService(userDetailsService);
    return authProvider;
  }

  private static String[] permittedHtmlRequests() {
    return new String[] {
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/ws/**",
      "/error",
      "/login",
      "/login-do",
    };
  }
}
