package com.greenfoxacademy.homeworkfoxprog.security.jwt;

import java.util.Optional;

public interface JwtService {

  String createToken(String username);

  Optional<String> extractUsernameFromToken(String token);
}
