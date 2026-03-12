package com.thewitchcat.meetingroombooking.api.dto.user;

import java.util.UUID;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserResponseDto(
  UUID id,
  String name,
  String email
) {}
