package com.greenfoxacademy.homeworkfoxprog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginDTO {
  private String username;
  private String password;
}
