package com.thewitchcat.meetingroombooking.api.dto.room;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

@Introspected
public record RoomFilterRequestDto(
  @Nullable String name,
  @Nullable Integer capacity,
  @Nullable Boolean active
) {}
