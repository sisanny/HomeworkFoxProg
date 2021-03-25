package com.greenfoxacademy.homeworkfoxprog.controllers;

import com.greenfoxacademy.homeworkfoxprog.dto.FoxDTO;
import com.greenfoxacademy.homeworkfoxprog.errors.FoxException;
import com.greenfoxacademy.homeworkfoxprog.errors.ResponseError;
import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.Mood;
import com.greenfoxacademy.homeworkfoxprog.models.Treat;
import com.greenfoxacademy.homeworkfoxprog.services.AuthService;
import com.greenfoxacademy.homeworkfoxprog.services.FoxService;
import com.greenfoxacademy.homeworkfoxprog.services.TreatService;
import com.greenfoxacademy.homeworkfoxprog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/fox")
public class FoxController {

  final TreatService treatService;

  final FoxService foxService;

  final AuthService authService;

  final UserService userService;

  public FoxController(FoxService foxService, UserService userService, AuthService authService, TreatService treatService) {
    this.foxService = foxService;
    this.userService = userService;
    this.authService = authService;
    this.treatService = treatService;
  }

  @PostMapping(path = "/create")
  public ResponseEntity<?> createFox(@RequestBody FoxDTO foxFromUser) {
    Fox newFox = Fox.builder()
                    .name(foxFromUser.getName())
                    .owner(authService.getCurrentUser())
                    .mood(Mood.OK)
                    .build();
    foxService.save(newFox);
    String owner = newFox.getOwner().getUsername();
    userService.addFox(newFox, newFox.getOwner());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(path = "/{name}")
  public ResponseEntity<?> getFoxByName(@PathVariable String name) {
    return ResponseEntity.ok(foxService.findFoxByName(name, authService.getCurrentUser()));
  }

  @GetMapping(path = "/myFoxes")
  public ResponseEntity<?> getFoxesOfCurrentUser() {
    return ResponseEntity.ok(foxService.findFoxesOfCurrentUser(authService.getCurrentUser()));
  }

  @GetMapping(path = ("/findNutrition/{filter}"))
  public ResponseEntity<?> getAllNutrition(@PathVariable String filter) {
    return ResponseEntity.ok(foxService.findAllPossibleFoodOrDrink(filter));
  }

  @PostMapping(path = "/setNutrition/{foxId}/{filter}/{chosenNutrition}")
  public ResponseEntity<?> setNutrition(@PathVariable String chosenNutrition,
                                        @PathVariable String filter,
                                        @PathVariable long foxId) {

    Fox foxToSetNutrition = foxService.findMyFoxById(foxId, authService.getCurrentUser());
    if (foxToSetNutrition != null) {
      try {
        foxService.setNutrition(chosenNutrition, filter, foxId);
        return ResponseEntity.ok(foxToSetNutrition);
      } catch (FoxException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
      }
    }
    return ResponseEntity.badRequest()
    .body(new ResponseError("You do not have a fox with this id:("));
  }

  @PostMapping(path = "/treat/{foxId}")
  public ResponseEntity<?> giveTreatToFox(@PathVariable long foxId, @RequestParam String treatName) {
    Fox foxToGiveTreat = foxService.findMyFoxById(foxId, authService.getCurrentUser());
    Treat treat = treatService.createTreat(treatName);

    if (foxToGiveTreat != null) {
      return ResponseEntity.ok(foxService.giveTreat(treat, foxToGiveTreat));
    }
    return ResponseEntity.badRequest()
        .body(new FoxException("You do not have a fox with this id:("));
  }
}
