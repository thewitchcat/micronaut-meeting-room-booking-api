package com.thewitchcat.meetingroombooking.api.controller;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thewitchcat.meetingroombooking.api.dto.room.RoomRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.user.UserRequestDto;
import com.thewitchcat.meetingroombooking.api.repository.BookingRepository;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;
import com.thewitchcat.meetingroombooking.api.repository.UserRepository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;

@MicronautTest
public class BookingControllerTest {
  
  @Inject
  BookingRepository bookingRepository;

  @Inject
  UserRepository userRepository;

  @Inject
  RoomRepository roomRepository;

  @BeforeEach
  @AfterEach
  void cleanup() {
    bookingRepository.deleteAll();
    userRepository.deleteAll();
    roomRepository.deleteAll();
  }
  
  @Test
  public void testCreateBookingEndpoint(RequestSpecification spec) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime endTime = startTime.plusHours(4).withMinute(0).withSecond(0).withNano(0);

    UserRequestDto user = new UserRequestDto("Mike", "mike@example.com");
    RoomRequestDto room = new RoomRequestDto("Room #1", 40, true);

    spec
      .contentType(ContentType.JSON)
      .body(user)
      .when()
        .post("/users");
    
    spec
      .contentType(ContentType.JSON)
      .body(room)
      .when()
        .post("/rooms");

    UUID userId = spec
      .when()
        .get("/users")
      .then()
        .extract()
        .jsonPath()
        .getUUID("content[0].id");

    UUID roomId = spec
      .when()
        .get("/rooms")
      .then()
        .extract()
        .jsonPath()
        .getUUID("content[0].id");
    
    Map<String, Object> booking = Map.of(
      "startTime", startTime.format(formatter),
      "endTime", endTime.format(formatter),
      "userId", userId,
      "roomId", roomId
    );

    spec
      .contentType(ContentType.JSON)
      .body(booking)
      .when()
        .post("/bookings")
      .then()
        .statusCode(201)
        .contentType("application/json")
        .body("startTime", equalTo(startTime.format(formatter)))
        .body("endTime", equalTo(endTime.format(formatter)))
        .body("status", equalTo("CONFIRMED"));
  }
  
  @Test
  public void testGetAllBookingsEndpoint(RequestSpecification spec) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime endTime = startTime.plusHours(4).withMinute(0).withSecond(0).withNano(0);

    UserRequestDto user = new UserRequestDto("Mike", "mike@example.com");
    RoomRequestDto room = new RoomRequestDto("Room #1", 40, true);

    spec
      .contentType(ContentType.JSON)
      .body(user)
      .when()
        .post("/users");
    
    spec
      .contentType(ContentType.JSON)
      .body(room)
      .when()
        .post("/rooms");

    UUID userId = spec
      .when()
        .get("/users")
      .then()
        .extract()
        .jsonPath()
        .getUUID("content[0].id");

    UUID roomId = spec
      .when()
        .get("/rooms")
      .then()
        .extract()
        .jsonPath()
        .getUUID("content[0].id");
    
    Map<String, Object> booking = Map.of(
      "startTime", startTime.format(formatter),
      "endTime", endTime.format(formatter),
      "userId", userId,
      "roomId", roomId
    );

    spec
      .contentType(ContentType.JSON)
      .body(booking)
      .when()
        .post("/bookings");

    spec
      .when()
        .get("/bookings")
      .then()
        .statusCode(200)
        .contentType("application/json")
        .body("content.size()", equalTo(1))
        .body("content[0].startTime", equalTo(startTime.format(formatter)))
        .body("content[0].endTime", equalTo(endTime.format(formatter)))
        .body("content[0].status", equalTo("CONFIRMED"));
  }
}
