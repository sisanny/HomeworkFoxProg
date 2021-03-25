package com.greenfoxacademy.homeworkfoxprog.services;



import com.greenfoxacademy.homeworkfoxprog.dto.LoggedInUserDTO;
import com.greenfoxacademy.homeworkfoxprog.dto.RegisterDTO;
import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.User;

import java.util.List;

public interface UserService {
  User addUser(User user);

  void addFox(Fox fox, User user);

  User findByUsername(String username);

  boolean existByUsername(String username);

  LoggedInUserDTO validateUser(RegisterDTO login);

  long getIdByUsername(String username);

  User findById(long id);

  boolean existsById(long id);
}