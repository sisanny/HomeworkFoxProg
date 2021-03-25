package com.greenfoxacademy.homeworkfoxprog.errors;

public class FoxException extends RuntimeException{
  public FoxException(String exMessage, Exception exception) {
    super(exMessage, exception);
  }

  public FoxException(String exMessage) {
    super(exMessage);
  }
}
