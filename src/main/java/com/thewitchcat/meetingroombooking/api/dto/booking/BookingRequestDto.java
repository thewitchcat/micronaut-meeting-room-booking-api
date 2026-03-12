package com.thewitchcat.meetingroombooking.api.dto.booking;

import java.time.LocalDateTime;
import java.util.UUID;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record BookingRequestDto(
  @NotNull
  LocalDateTime startTime,
  
  @NotNull
  LocalDateTime endTime,

  @NotNull
  UUID userId,

  @NotNull
  UUID roomId
) {}
