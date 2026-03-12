package com.thewitchcat.meetingroombooking.api.dto.user;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Serdeable
public record UserRequestDto(
  @NotNull
  @NotBlank
  @Size(min = 3, max = 30)
  String name,

  @NotNull
  @NotBlank
  @Email
  @Size(min = 5, max = 320)
  String email
) {}
