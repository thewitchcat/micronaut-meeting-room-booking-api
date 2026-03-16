package com.thewitchcat.meetingroombooking.api.repository;

import java.util.Optional;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Room;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface RoomRepository extends PageableRepository<Room, UUID> {
  Optional<Room> findByName(String name);

  @Query(
    value = """
      SELECT * FROM rooms
      WHERE (:name IS NULL OR name ILIKE '%' || :name || '%')
        AND (:capacity IS NULL OR capacity = :capacity)
        AND (:active IS NULL OR active = :active)
    """,
    countQuery = """
      SELECT COUNT(*) FROM rooms
      WHERE (:name IS NULL OR name ILIKE '%' || :name || '%')
        AND (:capacity IS NULL OR capacity = :capacity)
        AND (:active IS NULL OR active = :active)
    """
  )
  Page<Room> findFiltered(
    @Nullable String name,
    @Nullable Integer capacity,
    @Nullable Boolean active,
    Pageable rooms
  );
}
