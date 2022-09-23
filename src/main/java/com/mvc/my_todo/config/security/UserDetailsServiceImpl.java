package com.mvc.my_todo.config.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mvc.my_todo.models.User;
import com.mvc.my_todo.services.UserService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userService.findByEmail(username);

    if(!user.isPresent()) {
      throw new UsernameNotFoundException("User not found");
    }
    return (UserDetails) user.get();
  }

}
