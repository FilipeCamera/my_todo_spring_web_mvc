package com.mvc.my_todo.config.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mvc.my_todo.models.User;
import com.mvc.my_todo.services.UserService;

public class TokenFilter extends OncePerRequestFilter {

  private TokenService tokenService;

  private UserService userService;

  public TokenFilter(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String token = recoverToken(request);

    boolean valid = tokenService.isValidToken(token);

    if(valid) {
      authenticate(token);
    }

    filterChain.doFilter(request, response);
  }

  private void authenticate(String token) {
    UUID userId = tokenService.getUserID(token);

    User user = userService.findById(userId);

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String recoverToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7, token.length());
  }
  
}
