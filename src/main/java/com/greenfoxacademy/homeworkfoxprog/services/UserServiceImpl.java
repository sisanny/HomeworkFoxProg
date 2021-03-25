package com.greenfoxacademy.homeworkfoxprog.services;

import com.greenfoxacademy.homeworkfoxprog.dto.LoggedInUserDTO;
import com.greenfoxacademy.homeworkfoxprog.dto.RegisterDTO;
import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.User;
import com.greenfoxacademy.homeworkfoxprog.repositories.UserRepository;
import com.greenfoxacademy.homeworkfoxprog.security.jwt.JwtServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final JwtServiceImpl jwtService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository,
                         BCryptPasswordEncoder encoder,
                         JwtServiceImpl jwtService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = encoder;
    this.jwtService = jwtService;
  }

  @Override
  public User addUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User findById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public long getIdByUsername(String username) {
    return userRepository.findByUsername(username).getId();
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public boolean existByUsername(String username) {

    return userRepository.existsByUsername(username);
  }

  @Override
  public LoggedInUserDTO validateUser(RegisterDTO login) {
    User storedUser = findByUsername(login.getUsername());
    if (bCryptPasswordEncoder.matches(login.getPassword(), storedUser.getPassword())) {
      return new LoggedInUserDTO(storedUser.getUsername(), jwtService.createToken(storedUser.getUsername()));
    }
    return null;
  }

  /*@Override
  public User updateUserRole(Role role, long id) {
    User oldUser = userRepository.findById(id);
    if (oldUser.getRoles().stream().filter(p -> p.getName().equals(role.getName())).findAny().orElse(null) == null) {
      oldUser.addToRoles(role);
      userRepository.save(oldUser);
    }
    return oldUser;
  }*/

  @Override
  public boolean existsById(long id) {
    return userRepository.existsById(id);
  }

  @Override
  public void addFox(Fox fox, User user) {
    user.getFoxes().add(fox);
    userRepository.save(user);
  }

  /*@Override
  public void addRole(Role role, User user) {
    user.getRoles().add(role);
    userRepository.save(user);
  }*/

}