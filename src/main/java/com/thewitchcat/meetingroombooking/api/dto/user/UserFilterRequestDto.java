package com.thewitchcat.meetingroombooking.api.dto.user;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

@Introspected
public record UserFilterRequestDto(
  @Nullable String name,
  @Nullable String email
) {}
