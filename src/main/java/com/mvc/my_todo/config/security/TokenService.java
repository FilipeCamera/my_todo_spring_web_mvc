package com.mvc.my_todo.config.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mvc.my_todo.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
  
  @Value("${my_todo.jwt.expiration}")
  private String expiration;

  public String generateToken(Authentication authentication) {
    Date now = new Date();
    Date expiresIn = new Date(now.getTime() + Long.parseLong(expiration));

    User logged = (User) authentication.getPrincipal();

    return Jwts.builder()
              .setIssuer("my_todo")
              .setSubject(logged.getId().toString())
              .setIssuedAt(now)
              .setExpiration(expiresIn)
              .signWith(SignatureAlgorithm.HS256, "test")
              .compact();
  }

  public boolean isValidToken(String token) {
    try {
      Jwts
        .parser()
        .setSigningKey("test")
        .parseClaimsJws(token);
      
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public UUID getUserID(String token) {
    Claims claims = Jwts.parser().setSigningKey("test").parseClaimsJws(token).getBody();

    return UUID.fromString(claims.getId());
  }
}
