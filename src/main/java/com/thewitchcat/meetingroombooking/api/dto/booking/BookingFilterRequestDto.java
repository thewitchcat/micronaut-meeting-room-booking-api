package com.thewitchcat.meetingroombooking.api.dto.booking;

import java.time.LocalDateTime;

import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
public record BookingFilterRequestDto(
  
  @Parameter(schema = @Schema(type = "string", format = "local-date-time", example = "2026-03-19T08:00:00"))
  @Nullable LocalDateTime startTime,

  @Parameter(schema = @Schema(type = "string", format = "local-date-time", example = "2026-03-19T10:00:00"))
  @Nullable LocalDateTime endTime,
  
  @Nullable BookingStatus status
) {}
