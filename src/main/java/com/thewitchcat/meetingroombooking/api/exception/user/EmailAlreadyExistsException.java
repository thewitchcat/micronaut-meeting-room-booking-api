package com.thewitchcat.meetingroombooking.api.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException() {
    super("Email already exists");
  }

}
