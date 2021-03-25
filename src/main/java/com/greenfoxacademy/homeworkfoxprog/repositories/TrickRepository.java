package com.greenfoxacademy.homeworkfoxprog.repositories;

import com.greenfoxacademy.homeworkfoxprog.models.Trick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrickRepository extends CrudRepository<Trick, Long> {
  Trick findById(long id);
}
