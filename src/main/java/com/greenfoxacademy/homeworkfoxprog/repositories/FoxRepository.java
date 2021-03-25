package com.greenfoxacademy.homeworkfoxprog.repositories;

import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoxRepository extends CrudRepository<Fox, Long> {
  Fox findById(long id);
  List<Fox> findAll();
}
