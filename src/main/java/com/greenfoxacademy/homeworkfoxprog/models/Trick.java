package com.greenfoxacademy.homeworkfoxprog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
@Entity
@Builder
public class Trick {

  @Id
  @GeneratedValue
  private long id;

  @Column
  private String name;

  @Column
  private int currentLevel;

  @Column
  private int maxLevel;

  @ManyToMany
  private List<Fox> foxes;
}
