package com.greenfoxacademy.homeworkfoxprog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String username;

  @Column
  private String password;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Fox> foxes;

  //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  //private Set<Role> roles = new HashSet<>();

  @Column
  private String role;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    //Set<Role> roles = this.getRoles();
    role = this.role;
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    /*for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }*/
    authorities.add(new SimpleGrantedAuthority(role));

    return authorities;
  }

  public User(String username) {
    username = this.username;
  }

  //public void addToRoles(Role newRole) {
    //this.roles.add(newRole);
  //}
}
