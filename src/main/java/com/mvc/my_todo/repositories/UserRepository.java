package com.mvc.my_todo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc.my_todo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  public Optional<User> findByEmail(String email);
}
