package com.thewitchcat.meetingroombooking.api.exception.user;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found with the given ID!");
  }
}
