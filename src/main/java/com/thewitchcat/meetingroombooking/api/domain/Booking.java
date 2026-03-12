package com.thewitchcat.meetingroombooking.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

@MappedEntity("bookings")
public class Booking {
  
  @Id
  private UUID id;

  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private BookingStatus status;
  private LocalDateTime createdAt;

  @MappedProperty("user_id")
  private UUID user;

  @MappedProperty("room_id")
  private UUID room;

  public Booking(
    UUID id, 
    LocalDateTime startTime, 
    LocalDateTime endTime, 
    BookingStatus status, 
    LocalDateTime createdAt, 
    UUID user, 
    UUID room
  ) {
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
    this.status = status;
    this.createdAt = createdAt;
    this.user = user;
    this.room = room;
  }

  public UUID getId() { return id; }
  public LocalDateTime getStartTime() { return startTime; }
  public LocalDateTime getEndTime() { return endTime; }
  public BookingStatus getStatus() { return status; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public UUID getUser() { return user; }
  public UUID getRoom() { return room; }

  public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
  public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
  public void setStatus(BookingStatus status) { this.status = status; }
  public void setUser(UUID user) { this.user = user; }
  public void setRoom(UUID room) { this.room = room; }
}
