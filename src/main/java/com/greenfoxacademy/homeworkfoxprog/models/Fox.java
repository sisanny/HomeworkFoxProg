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
public class Fox {

  @Id
  @GeneratedValue
  private long id;

  @Column
  private String name;

  @Column
  private String food;

  @Column
  private String drink;

  @Column
  private Mood mood;

  @Column
  private long treat;

  @ManyToOne
  private User owner;

  @JsonIgnore
  @ManyToMany
  private List<Trick> trickList;

  public Fox(User owner) {
    this.owner = owner;
  }
}
