package com.thewitchcat.meetingroombooking.api.exception.room;

public class RoomNotFoundException extends RuntimeException {
  public RoomNotFoundException() {
    super("Room not found with the given ID!");
  }
}
