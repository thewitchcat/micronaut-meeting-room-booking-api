package com.thewitchcat.meetingroombooking.api.controller;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.thewitchcat.meetingroombooking.api.dto.room.RoomRequestDto;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;

@MicronautTest
public class RoomControllerTest {

  @Inject
  RoomRepository repository;

  @AfterEach
  void cleanup() {
    repository.deleteAll();
  }
  
  @Test
  public void testCreateRoomEndpoint(RequestSpecification spec) {

    RoomRequestDto room = new RoomRequestDto(
      "Room #1", 
      40, 
      true
    );
    
    spec
      .contentType(ContentType.JSON)
      .body(room)
      .when()
        .post("/rooms")
      .then()
        .statusCode(201)
        .contentType("application/json")
        .body("name", equalTo("Room #1"))
        .body("capacity", equalTo(40))
        .body("active", equalTo(true));
  }
  
  @Test
  public void testGetAllRoomsEndpoint(RequestSpecification spec) {

    RoomRequestDto room = new RoomRequestDto(
      "Room #1", 
      30, 
      false
    );
    
    spec
      .contentType(ContentType.JSON)
      .body(room)
      .when()
        .post("/rooms");
    
    spec
      .when()
        .get("/rooms")
      .then()
        .statusCode(200)
        .contentType("application/json")
        .body("content.size()", equalTo(1))
        .body("content[0].name", equalTo("Room #1"))
        .body("content[0].capacity", equalTo(30))
        .body("content[0].active", equalTo(false));
  }
}
