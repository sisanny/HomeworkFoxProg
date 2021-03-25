package com.greenfoxacademy.homeworkfoxprog.repositories;

import com.greenfoxacademy.homeworkfoxprog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  Boolean existsByUsername(String username);

  User findById(long id);
}
