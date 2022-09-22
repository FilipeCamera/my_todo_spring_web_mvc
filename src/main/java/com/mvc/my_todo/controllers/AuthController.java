package com.mvc.my_todo.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mvc.my_todo.models.User;
import com.mvc.my_todo.services.RoleService;
import com.mvc.my_todo.services.UserService;

import dto.UserDTO;

@Controller
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;
  
  @GetMapping("register")
  public String create(UserDTO request) {
    return "register";
  }

  @PostMapping("new")
  public String createUser(@Valid UserDTO request, BindingResult result) {
    if(result.hasErrors() || !request.getPassword().equals(request.getConfirmPassword())) {
      return "register";
    }

    if(userService.findByEmail(request.getEmail())) {
      return "register";
    }

    var user = new User();
    var role = roleService.findByName("USER");

    BeanUtils.copyProperties(request, user);

    user.setRoles(Arrays.asList(role));

    userService.save(user);

    return "login";
  }

  @GetMapping("login")
  public String authenticate() {
    return "login";
  }
}
