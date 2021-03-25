package com.greenfoxacademy.homeworkfoxprog.services;

import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.Trick;
import com.greenfoxacademy.homeworkfoxprog.repositories.FoxRepository;
import com.greenfoxacademy.homeworkfoxprog.repositories.TrickRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrickServiceImpl implements TrickService {

  final TrickRepository trickRepository;
  final FoxRepository foxRepository;


  public TrickServiceImpl(TrickRepository trickRepository, FoxRepository foxRepository) {
    this.trickRepository = trickRepository;
    this.foxRepository = foxRepository;
  }

  @Override
  public Trick createTrick(String name, int maxLevel) {
    Trick newTrick = Trick.builder()
                          .name(name)
                          .currentLevel(0)
                          .maxLevel(maxLevel)
                          .build();
    trickRepository.save(newTrick);
    return newTrick;
  }

  @Override
  public String levelUp(long trickId, long foxId) {
    Trick trick = trickRepository.findById(trickId);
    Fox fox = foxRepository.findById(foxId);

    if (trick != null) {
      if (trick.getMaxLevel() > (trick.getCurrentLevel() + 1)) {
        trick.setCurrentLevel(trick.getCurrentLevel() + 1);
        trickRepository.save(trick);
        return "Congrats! You just leveled up " + fox.getName() + " !";
      } else if (trick.getMaxLevel() == (trick.getCurrentLevel() + 1)) {
        trick.setCurrentLevel(trick.getCurrentLevel() + 1);
        trickRepository.save(trick);
        return "Congrats! " + fox.getName() + " just finished learning this trick.";
      }
      return "Ops! Seems like, there is nothing left to learn about this trick to " + fox.getName() + ".";
    }
    return null;
  }

  @Override
  public List<Trick> findAll() {
    return (List<Trick>) trickRepository.findAll();
  }
}
