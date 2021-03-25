package com.greenfoxacademy.homeworkfoxprog.services;

import com.greenfoxacademy.homeworkfoxprog.models.Trick;

import java.util.List;

public interface TrickService {

  Trick createTrick(String name, int maxLevel);

  String levelUp(long trickId, long foxId);

  List<Trick> findAll();
}
