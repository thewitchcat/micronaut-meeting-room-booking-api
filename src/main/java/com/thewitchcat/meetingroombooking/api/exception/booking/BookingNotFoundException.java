package com.thewitchcat.meetingroombooking.api.exception.booking;

public class BookingNotFoundException extends RuntimeException {
  public BookingNotFoundException() {
    super("Booking not found with the given ID!");
  }
}
