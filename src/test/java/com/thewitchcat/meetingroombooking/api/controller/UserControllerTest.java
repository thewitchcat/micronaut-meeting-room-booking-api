package com.thewitchcat.meetingroombooking.api.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.thewitchcat.meetingroombooking.api.dto.user.UserRequestDto;
import com.thewitchcat.meetingroombooking.api.repository.UserRepository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;

import static org.hamcrest.Matchers.equalTo;

@MicronautTest()
public class UserControllerTest {

  @Inject
  UserRepository repository;
  
  @AfterEach
  void cleanup() {
    repository.deleteAll();
  }
  
  @Test
  public void testCreateUserEndpoint(RequestSpecification spec) {

    UserRequestDto user = new UserRequestDto(
      "Mike", 
      "mike@example.com"
    );
    
    spec
      .contentType(ContentType.JSON)
      .body(user)
      .when()
        .post("/users")
      .then()
        .statusCode(201)
        .contentType("application/json")
        .body("name", equalTo("Mike"))
        .body("email", equalTo("mike@example.com"));
  }
  
  @Test
  public void testGetAllUsersEndpoint(RequestSpecification spec) {

    UserRequestDto user = new UserRequestDto(
      "Mike", 
      "mike@example.com"
    );
    
    spec
      .contentType(ContentType.JSON)
      .body(user)
      .when()
        .post("/users");
    
    spec
      .when()
        .get("/users")
      .then()
        .statusCode(200)
        .contentType("application/json")
        .body("content.size()", equalTo(1))
        .body("content[0].name", equalTo("Mike"))
        .body("content[0].email", equalTo("mike@example.com"));
  }
}
