package com.greenfoxacademy.homeworkfoxprog.controllers;

import com.greenfoxacademy.homeworkfoxprog.dto.TrickDTO;
import com.greenfoxacademy.homeworkfoxprog.errors.ResponseError;
import com.greenfoxacademy.homeworkfoxprog.models.Trick;
import com.greenfoxacademy.homeworkfoxprog.services.TrickService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/trick")
public class TrickController {

  final TrickService trickService;

  public TrickController(TrickService trickService) {
    this.trickService = trickService;
  }

  @PostMapping(path = "/create")
  public ResponseEntity<?> createTrick(@RequestBody TrickDTO trickFromUser) {
    if (trickFromUser != null) {
      return ResponseEntity.ok(trickService.createTrick(trickFromUser.getName(),
                                                        trickFromUser.getMaxLevel()));
    }
    return ResponseEntity.badRequest()
                         .body(new ResponseError("The trick you gave is not complete"));
  }

  @PostMapping(path = "/practice/{trickId}/{foxId}")
  public ResponseEntity<?> practiceTrick(@PathVariable long trickId, @PathVariable long foxId) {
    String result = trickService.levelUp(trickId, foxId);
    if (result != null) {
      return ResponseEntity.ok(result);
    }
    return ResponseEntity.badRequest().body(new ResponseError
                                            ("Ops, looks like there is no such trick yet"));
  }

  @GetMapping(path = "/allTricks")
  public ResponseEntity<?> getAllTreats() {
    List<Trick> tricks = trickService.findAll();
    if (tricks != null && !tricks.isEmpty()){
      return ResponseEntity.ok(tricks);
    }
    return ResponseEntity.badRequest().body(new ResponseError("There is no tricks yet. Go create some!"));
  }
}
