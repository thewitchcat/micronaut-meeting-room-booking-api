package com.thewitchcat.meetingroombooking.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Room;
import com.thewitchcat.meetingroombooking.api.dto.room.RoomRequestDto;
import com.thewitchcat.meetingroombooking.api.exception.room.RoomAlreadyExistsException;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

@Singleton
public class RoomService {
  
  private final RoomRepository repository;

  public RoomService(RoomRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Room createRoom(RoomRequestDto payload) {
    
    String name = payload.name().trim();
    
    if (repository.findByName(name).isPresent()) {
      throw new RoomAlreadyExistsException(name);
    }
    
    Room room = new Room(
      UUID.randomUUID(), 
      name, 
      payload.capacity(), 
      payload.active(), 
      LocalDateTime.now()
    );

    repository.save(room);

    return room;
  }

  public List<Room> getAllRooms() {
    return repository.findAll();
  }
}
