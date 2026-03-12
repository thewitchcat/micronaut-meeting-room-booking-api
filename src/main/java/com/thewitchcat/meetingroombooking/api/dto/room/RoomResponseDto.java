package com.thewitchcat.meetingroombooking.api.dto.room;

import java.time.LocalDateTime;
import java.util.UUID;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RoomResponseDto(
  UUID id,
  String name,
  int capacity,
  boolean active,
  LocalDateTime createdAt
) {}
