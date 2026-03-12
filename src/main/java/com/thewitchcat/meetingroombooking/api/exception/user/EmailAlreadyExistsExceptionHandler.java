package com.thewitchcat.meetingroombooking.api.exception.user;

import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class EmailAlreadyExistsExceptionHandler implements ExceptionHandler<EmailAlreadyExistsException, HttpResponse<ErrorResponseDto>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<ErrorResponseDto> handle(HttpRequest req, EmailAlreadyExistsException ex) {
    return HttpResponse.status(HttpStatus.CONFLICT).body(new ErrorResponseDto("EMAIL_CONFLICT", ex.getMessage()));
  }
}
