package com.thewitchcat.meetingroombooking.api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Room;
import com.thewitchcat.meetingroombooking.api.dto.room.RoomFilterRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.room.RoomRequestDto;
import com.thewitchcat.meetingroombooking.api.exception.room.RoomAlreadyExistsException;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
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

  public Page<Room> getAllRooms(RoomFilterRequestDto filter, int page, int size) {
    Pageable rooms = Pageable.from(page, size);
    return repository.findFiltered(filter.name(), filter.capacity(), filter.active(), rooms);
  }
}
