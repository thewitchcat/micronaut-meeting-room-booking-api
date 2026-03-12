package com.thewitchcat.meetingroombooking.api.exception.booking;

import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class BookingNotFoundExceptionHandler implements ExceptionHandler<BookingNotFoundException, HttpResponse<ErrorResponseDto>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<ErrorResponseDto> handle(HttpRequest req, BookingNotFoundException ex) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto("BOOKING_NOT_FOUND", ex.getMessage()));
  }
}
