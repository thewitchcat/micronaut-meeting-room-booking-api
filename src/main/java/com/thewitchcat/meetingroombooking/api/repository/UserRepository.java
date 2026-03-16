package com.thewitchcat.meetingroombooking.api.repository;

import java.util.Optional;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.User;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends PageableRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  @Query(
    value = """
      SELECT * FROM users
      WHERE (:name IS NULL OR name ILIKE '%' || :name || '%')
        AND (:email IS NULL OR email = :email)
    """,
    countQuery = """
      SELECT COUNT(*) FROM users
      WHERE (:name IS NULL OR name ILIKE '%' || :name || '%')
        AND (:email IS NULL OR email = :email)
    """
  )
  Page<User> findFiltered(
    @Nullable String name,
    @Nullable String email,
    Pageable users
  );
}
