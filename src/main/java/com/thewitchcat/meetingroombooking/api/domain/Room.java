package com.thewitchcat.meetingroombooking.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity("rooms")
public class Room {
  
  @Id
  private UUID id;

  private String name;
  private int capacity;
  private boolean active;
  private LocalDateTime createdAt;

  public Room(UUID id, String name, int capacity, boolean active, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
    this.active = active;
    this.createdAt = createdAt;
  }

  public UUID getId() { return id; }
  public String getName() { return name; }
  public int getCapacity() { return capacity; }
  public boolean isActive() { return active; }
  public LocalDateTime getCreatedAt() { return createdAt; }

  public void setName(String name) { this.name = name; }
  public void setCapacity(int capacity) { this.capacity = capacity; }
  public void setActive(boolean active) { this.active = active; }
}
