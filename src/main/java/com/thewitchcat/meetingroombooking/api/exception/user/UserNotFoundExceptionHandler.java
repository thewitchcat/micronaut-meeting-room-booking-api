package com.thewitchcat.meetingroombooking.api.exception.user;

import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class UserNotFoundExceptionHandler implements ExceptionHandler<UserNotFoundException, HttpResponse<ErrorResponseDto>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<ErrorResponseDto> handle(HttpRequest req, UserNotFoundException ex) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto("USER_NOT_FOUND", ex.getMessage()));
  }
}
