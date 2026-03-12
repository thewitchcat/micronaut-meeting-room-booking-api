package com.thewitchcat.meetingroombooking.api.dto.room;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Serdeable
public record RoomRequestDto(
  @NotNull
  @NotBlank
  @Size(min = 3, max = 50)
  String name,

  @NotNull
  @Min(1)
  @Schema(minimum = "1")
  int capacity,

  @NotNull
  boolean active
) {}
