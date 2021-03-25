package com.greenfoxacademy.homeworkfoxprog.models;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
@Entity
@Builder
public class Treat {

  @Id
  @GeneratedValue
  private long id;

  @Column
  private String name;

}
