package com.thewitchcat.meetingroombooking.api.exception.room;

import java.util.Map;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class RoomNotFoundExceptionHandler implements ExceptionHandler<RoomNotFoundException, HttpResponse<?>> {
  @Override
  @SuppressWarnings("rawtypes")
  public HttpResponse<?> handle(HttpRequest req, RoomNotFoundException ex) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(
      Map.of(
        "error", ex.getMessage()
      )
    );
  }
}
