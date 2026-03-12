package com.thewitchcat.meetingroombooking.api.exception.room;

import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class RoomAlreadyExistsExceptionHandler implements ExceptionHandler<RoomAlreadyExistsException, HttpResponse<ErrorResponseDto>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<ErrorResponseDto> handle(HttpRequest req, RoomAlreadyExistsException ex) {
    return HttpResponse.status(HttpStatus.CONFLICT).body(new ErrorResponseDto("ROOM_CONFLICT", ex.getMessage()));
  }
}
