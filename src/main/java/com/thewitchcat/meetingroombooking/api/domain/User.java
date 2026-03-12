package com.thewitchcat.meetingroombooking.api.domain;

import java.util.UUID;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity("users")
public class User {
  
  @Id
  private UUID id;

  private String name;
  private String email;
  
  public User(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public UUID getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }

  public void setName(String name) { this.name = name; }
  public void setEmail(String email) { this.email = email; }
}
