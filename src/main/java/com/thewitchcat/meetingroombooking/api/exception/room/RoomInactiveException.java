package com.thewitchcat.meetingroombooking.api.exception.room;

public class RoomInactiveException extends RuntimeException {
  public RoomInactiveException() {
    super("The selected room is inactive.");
  }
}
