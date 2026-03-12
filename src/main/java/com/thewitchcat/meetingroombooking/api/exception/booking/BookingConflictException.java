package com.thewitchcat.meetingroombooking.api.exception.booking;

public class BookingConflictException extends RuntimeException {
  public BookingConflictException() {
    super("The room is already booked for the selected time range.");
  }
}
