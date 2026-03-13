package com.thewitchcat.meetingroombooking.api.dto;

import java.util.List;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record PagedResponseDto<T>(
  List<T> content,
  int page,
  int size,
  long total
) {}
