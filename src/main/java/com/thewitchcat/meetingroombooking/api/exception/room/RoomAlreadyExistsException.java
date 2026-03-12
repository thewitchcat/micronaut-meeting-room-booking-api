package com.thewitchcat.meetingroombooking.api.exception.room;

public class RoomAlreadyExistsException extends RuntimeException {
  public RoomAlreadyExistsException(String name) {
    super("Room with name '" + name + "' already exists!");
  }
}
