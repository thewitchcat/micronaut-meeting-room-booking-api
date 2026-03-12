package com.thewitchcat.meetingroombooking.api.repository;

import java.util.Optional;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.User;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends PageableRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}
