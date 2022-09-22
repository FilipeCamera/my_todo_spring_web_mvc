package com.mvc.my_todo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc.my_todo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  public Role findByName(String role);
}
