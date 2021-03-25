package com.greenfoxacademy.homeworkfoxprog.repositories;


import com.greenfoxacademy.homeworkfoxprog.models.Treat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatRepository extends CrudRepository<Treat, Long> {
}
