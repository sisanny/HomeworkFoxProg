package com.greenfoxacademy.homeworkfoxprog.services;


import com.greenfoxacademy.homeworkfoxprog.models.Treat;
import com.greenfoxacademy.homeworkfoxprog.repositories.TreatRepository;
import org.springframework.stereotype.Service;

@Service
public class TreatServiceImpl implements TreatService {

  final TreatRepository treatRepository;

  public TreatServiceImpl(TreatRepository treatRepository) {
    this.treatRepository = treatRepository;
  }

  @Override
  public Treat createTreat(String treat) {
    Treat newTreat = Treat.builder()
                          .name(treat)
                          .build();

    return treatRepository.save(newTreat);
  }
}
