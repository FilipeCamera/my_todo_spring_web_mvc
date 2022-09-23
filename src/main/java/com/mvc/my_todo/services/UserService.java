package com.mvc.my_todo.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.my_todo.models.User;
import com.mvc.my_todo.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findById(UUID id) {
    return userRepository.findById(id).get();
  }

  public User save(User user) {
    return userRepository.save(user);
  }
}
