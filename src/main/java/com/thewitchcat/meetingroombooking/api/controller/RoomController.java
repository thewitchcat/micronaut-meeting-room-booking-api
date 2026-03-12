package com.thewitchcat.meetingroombooking.api.controller;

import java.util.List;

import com.thewitchcat.meetingroombooking.api.domain.Room;
import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;
import com.thewitchcat.meetingroombooking.api.dto.room.RoomRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.room.RoomResponseDto;
import com.thewitchcat.meetingroombooking.api.service.RoomService;

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

@Controller("/rooms")
@Tag(name = "Room", description = "Operations related to rooms")
@ExecuteOn(TaskExecutors.BLOCKING)
public class RoomController {
  
  protected final RoomService service;

  public RoomController(RoomService service) {
    this.service = service;
  }

  @Get
  @ApiResponse(responseCode = "200", description = "List of Rooms")
  @Operation(summary = "List Rooms", description = "List registered Rooms on the system")
  public HttpResponse<List<RoomResponseDto>> getAllRooms() {
    
    List<Room> listOfRooms = service.getAllRooms();

    List<RoomResponseDto> res = listOfRooms.stream().map(
      room -> new RoomResponseDto(
        room.getId(),
        room.getName(),
        room.getCapacity(),
        room.isActive(),
        room.getCreatedAt()
      )
    ).toList();

    return HttpResponse.ok(res);
  }
  
  @Post
  @Operation(summary = "Create Room", description = "Create a new Room to the system")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Room created successfully",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = RoomResponseDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "Room with the same name is already exists",
      content = @Content(
        mediaType = "application/json",
        schema =  @Schema(implementation = ErrorResponseDto.class)
      )
    )
  })
  public HttpResponse<RoomResponseDto> createRoom(@Valid @Body RoomRequestDto payload) {

    Room room = service.createRoom(payload);
    
    RoomResponseDto res = new RoomResponseDto(
      room.getId(),
      room.getName(),
      room.getCapacity(),
      room.isActive(),
      room.getCreatedAt()
    );

    return HttpResponse.created(res);
  }
}
