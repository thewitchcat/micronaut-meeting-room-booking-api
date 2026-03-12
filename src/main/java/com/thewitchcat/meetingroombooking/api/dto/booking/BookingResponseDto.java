package com.thewitchcat.meetingroombooking.api.dto.booking;

import java.time.LocalDateTime;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record BookingResponseDto(
  UUID id,
  LocalDateTime startTime,
  LocalDateTime endTime,
  BookingStatus status,
  LocalDateTime createdAt,
  UUID userId,
  UUID roomId
) {}
