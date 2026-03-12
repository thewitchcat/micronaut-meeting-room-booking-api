package com.thewitchcat.meetingroombooking.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of a booking")
public enum BookingStatus {
  CONFIRMED,
  CANCELLED
}
