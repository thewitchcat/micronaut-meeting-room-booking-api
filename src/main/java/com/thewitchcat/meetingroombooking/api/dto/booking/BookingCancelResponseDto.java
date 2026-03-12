package com.thewitchcat.meetingroombooking.api.dto.booking;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

@Serdeable
public record BookingCancelResponseDto(
  @Schema(description = "Human-readable cancellation confirmation message")
  String message,

  @Schema(description = "Updated booking after cancellation")
  BookingResponseDto booking
) {}
