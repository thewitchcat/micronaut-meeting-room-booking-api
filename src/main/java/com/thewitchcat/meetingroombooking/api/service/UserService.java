package com.thewitchcat.meetingroombooking.api.service;

import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.User;
import com.thewitchcat.meetingroombooking.api.dto.user.UserRequestDto;
import com.thewitchcat.meetingroombooking.api.exception.user.EmailAlreadyExistsException;
import com.thewitchcat.meetingroombooking.api.repository.UserRepository;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

@Singleton
public class UserService {
  
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public User createUser(UserRequestDto payload) {
    
    String email = payload.email().trim();
    
    if (repository.findByEmail(payload.email()).isPresent()) {
      throw new EmailAlreadyExistsException();
    }

    User user = new User(
      UUID.randomUUID(),
      payload.name().trim(),
      email
    );

    repository.save(user);

    return user;
  }

  public Page<User> getAllUsers(int page, int size) {
    Pageable users = Pageable.from(page, size);
    return repository.findAll(users);
  }
}
