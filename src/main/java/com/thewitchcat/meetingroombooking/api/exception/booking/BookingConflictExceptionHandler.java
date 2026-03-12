package com.thewitchcat.meetingroombooking.api.exception.booking;

import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class BookingConflictExceptionHandler implements ExceptionHandler<BookingConflictException, HttpResponse<ErrorResponseDto>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<ErrorResponseDto> handle(HttpRequest req, BookingConflictException ex) {
    return HttpResponse.status(HttpStatus.CONFLICT).body(new ErrorResponseDto("BOOKING_CONFLICT", ex.getMessage()));
  }
}
