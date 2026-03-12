package com.thewitchcat.meetingroombooking.api.exception.room;

import java.util.Map;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class RoomInactiveExceptionHandler implements ExceptionHandler<RoomInactiveException, HttpResponse<?>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<?> handle(HttpRequest req, RoomInactiveException ex) {
    return HttpResponse.status(HttpStatus.FORBIDDEN).body(
      Map.of(
        "error", ex.getMessage()
      )
    );
  }
}
