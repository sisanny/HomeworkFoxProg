package com.greenfoxacademy.homeworkfoxprog.controllers;

import com.greenfoxacademy.homeworkfoxprog.dto.LoggedInUserDTO;
import com.greenfoxacademy.homeworkfoxprog.dto.RegisterDTO;
import com.greenfoxacademy.homeworkfoxprog.errors.ResponseError;
import com.greenfoxacademy.homeworkfoxprog.models.User;
import com.greenfoxacademy.homeworkfoxprog.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  //final RoleServiceImpl roleService;
  final BCryptPasswordEncoder bCryptPasswordEncoder;
  final UserService userService;

  public AuthController(
      UserService userService,
      BCryptPasswordEncoder encoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = encoder;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO newUser) {

    if (newUser.getUsername() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username cannot be empty"));
    } else if (newUser.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("password cannot be empty"));
    } else if (newUser.getUsername() == null && newUser.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username and password cannot be empty"));
    } else if (userService.existByUsername(newUser.getUsername())) {
      return ResponseEntity.badRequest().body(new ResponseError("username already exists"));
    } else {
      User user = User.builder()
          .username(newUser.getUsername())
          .password(bCryptPasswordEncoder.encode(newUser.getPassword()))
          .role("user")
          .build();
      //user.addToRoles(roleService.findByName("user"));
      userService.addUser(user);
      return ResponseEntity.ok().build();
    }
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@RequestBody RegisterDTO login) {
    if (login.getUsername() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username must be set"));
    } else if (login.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("password must be set"));
    } else if (login.getUsername() == null && login.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username and password must be set"));
    } else if (!userService.existByUsername(login.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new ResponseError("user does not exists with the given username password combination"));
    } else {
      LoggedInUserDTO loggedInUser = userService.validateUser(login);
      return ResponseEntity.ok(loggedInUser);
    }
  }
}
