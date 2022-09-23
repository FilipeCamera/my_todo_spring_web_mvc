package com.mvc.my_todo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
  
  @Id
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @Column(length = 36, nullable = false, unique = true)
  private UUID id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "update_at", nullable = false, updatable = true)
  private Date updateAt;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable( 
        name = "user_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id", table = "users"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id", table = "roles"))
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Todo> todos = new ArrayList<>();

  public UUID getId() {
    return id;
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    
    return this.password;
  }
  
  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    
    return this.roles;
  }

  @Override
  public String getUsername() {
    
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    
    return true;
  }

  @Override
  public boolean isEnabled() {
    
    return true;
  }
}
