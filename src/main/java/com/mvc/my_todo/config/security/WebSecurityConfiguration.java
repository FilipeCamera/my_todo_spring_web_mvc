package com.mvc.my_todo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mvc.my_todo.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests((requests) -> 
      requests.antMatchers("/")
        .permitAll()
        .antMatchers("/dashboard")
        .authenticated()
        .and()
        .addFilterBefore(new TokenFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class)
    )
    .formLogin((form) -> 
    form.loginPage("/login")
    ).logout((logout) -> logout.permitAll());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
