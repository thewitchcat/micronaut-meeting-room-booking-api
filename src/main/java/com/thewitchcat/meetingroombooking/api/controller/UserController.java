package com.thewitchcat.meetingroombooking.api.controller;

import java.util.List;

import com.thewitchcat.meetingroombooking.api.domain.User;
import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;
import com.thewitchcat.meetingroombooking.api.dto.user.UserRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.user.UserResponseDto;
import com.thewitchcat.meetingroombooking.api.service.UserService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller("/users")
@Tag(name = "User", description = "Operations related to users")
@ExecuteOn(TaskExecutors.BLOCKING)
public class UserController {
  
  protected final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @Get
  @ApiResponse(responseCode = "200", description = "List of Users")
  @Operation(summary = "List Users", description = "List registered Users on the system")
  public HttpResponse<List<UserResponseDto>> getAllUsers() {
    
    List<User> listOfUsers = service.getAllUsers();

    List<UserResponseDto> res = listOfUsers.stream().map(
      user -> new UserResponseDto(
        user.getId(),
        user.getName(),
        user.getEmail()
      )
    ).toList();

    return HttpResponse.ok(res);
  }

  @Post
  @Operation(summary = "Create User", description = "Register User account to the system")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "User account created successfully",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = UserResponseDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "User with the same email is already exists",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorResponseDto.class)
      )
    )
  })
  public HttpResponse<UserResponseDto> createUser(@Valid @Body UserRequestDto payload) {

    User user = service.createUser(payload);
    
    UserResponseDto res = new UserResponseDto(
      user.getId(),
      user.getName(),
      user.getEmail()
    );
    
    return HttpResponse.created(res);
  }
}
