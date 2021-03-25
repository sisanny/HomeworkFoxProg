package com.greenfoxacademy.homeworkfoxprog.services;

import com.greenfoxacademy.homeworkfoxprog.errors.ResponseError;
import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.Mood;
import com.greenfoxacademy.homeworkfoxprog.models.Treat;
import com.greenfoxacademy.homeworkfoxprog.models.User;
import com.greenfoxacademy.homeworkfoxprog.repositories.FoxRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoxServiceImpl implements FoxService {

  final FoxRepository foxRepository;

  public FoxServiceImpl(FoxRepository foxRepository) {
    this.foxRepository = foxRepository;
  }

  @Override
  public Fox save(Fox fox) {
    return foxRepository.save(fox);
  }

  @Override
  public List<Fox> findAll() {
    return foxRepository.findAll();
  }

  @Override
  public String giveTreat(Treat treat, Fox fox) {
      if (fox.getMood().toString().equals("SAD")) {
        fox.setMood(Mood.OK);
      } else {
        fox.setMood(Mood.HAPPY);
      }
      fox.setTreat(System.currentTimeMillis());
      return fox.getName() + " is " + fox.getMood() + " because of your " + treat.getName() + " treat.";
  }


  @Override
  public Fox findMyFoxById(long id, User user) {
    Fox fox = foxRepository.findById(id);
    /*if (user.getFoxes().contains(fox)) {
      return fox;
    }*/
    for (int i = 0; i < user.getFoxes().size(); i++) {
      if (user.getFoxes().get(i).getId() == id) {
        return fox;
      }
    }
    return null;
  }

  @Override
  public Fox findFoxByName(String name, User user) {
     Optional<Fox> fox = user.getFoxes().stream()
                              .filter(f -> f.getName().equals(name))
                              .findFirst();

     return fox.orElse(null);
  }

  @Override
  public List<Fox> findFoxesOfCurrentUser(User user) {
    List<Fox> foxesOfCurrentUser = foxRepository.findAll()
                                    .stream()
                                    .filter(fox -> fox.getOwner().getUsername().equals(user.getUsername()))
                                    .collect(Collectors.toList());
    return foxesOfCurrentUser;
  }

  @Override
  public List<String> findAllPossibleFoodOrDrink(String filter) {

    try {
      File resource;
      if (filter.equals("food")) {
        resource = new ClassPathResource("static/foodList.txt").getFile();
      } else {
        resource = new ClassPathResource("static/drinkList.txt").getFile();
      }
      List<String> filteredResourceList = new ArrayList<String>(Files.readAllLines(resource.toPath()));
      return filteredResourceList;

    } catch (NoSuchFileException e) {
      new ResponseError("The file which contains the list of food is missing:(");
    } catch (IOException e) {
      new ResponseError("Error");
    }
    return null;
  }

  @Override
  public void setNutrition(String chosenNutrition, String filter, long id) {
    Fox foxToSetNutrition = foxRepository.findById(id);

    if (findAllPossibleFoodOrDrink(filter).contains(chosenNutrition)) {
      if (filter.equals("food")) {
        foxToSetNutrition.setFood(chosenNutrition);
      } else {
        foxToSetNutrition.setDrink(chosenNutrition);
      }
      foxRepository.save(foxToSetNutrition);
    } else {
       new ResponseError("Your chosen nutrition does not exist. Please choose from the list");
    }
  }

  @Scheduled(fixedRate = 30000)
  public void scheduledMoodChange() {
    List<Fox> foxes = foxRepository.findAll();
    long currentTime = System.currentTimeMillis();

    for (int i = 0; i < foxes.size(); i++) {
      Fox currentFox = foxes.get(i);
      long treatTime = currentFox.getTreat();

      if ((currentTime - treatTime) > 60000 && currentFox.getMood().toString().equals("HAPPY")) {
        currentFox.setMood(Mood.OK);
      } else if ((currentTime - treatTime) > 60000 && currentFox.getMood().toString().equals("OK")) {
        currentFox.setMood(Mood.SAD);
      }
      foxRepository.save(currentFox);
    }
  }
}
