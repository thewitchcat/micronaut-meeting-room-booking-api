package com.thewitchcat.meetingroombooking.api.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponseDto(
  String error,
  String message
) {}
