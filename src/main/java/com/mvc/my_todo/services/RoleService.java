package com.mvc.my_todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.my_todo.models.Role;
import com.mvc.my_todo.repositories.RoleRepository;

@Service
public class RoleService {
  
  @Autowired
  private RoleRepository roleRepository;

  public Role findByName(String role) {
    return roleRepository.findByName(role);
  }
}
