package com.greenfoxacademy.homeworkfoxprog.services;

import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.Treat;
import com.greenfoxacademy.homeworkfoxprog.models.User;

import java.util.List;

public interface FoxService {

  Fox save(Fox fox);

  //Fox findFoxById(long id);

  List<Fox> findAll();

  String giveTreat(Treat treat, Fox fox);

  Fox findMyFoxById(long id, User user);

  Fox findFoxByName(String name, User user);

  List<Fox> findFoxesOfCurrentUser(User user);

  List<String> findAllPossibleFoodOrDrink(String filter);

  void setNutrition(String chosenNutrition, String filter, long id);
}
