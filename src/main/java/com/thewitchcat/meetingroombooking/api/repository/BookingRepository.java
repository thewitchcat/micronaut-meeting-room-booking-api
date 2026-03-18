package com.thewitchcat.meetingroombooking.api.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Booking;
import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface BookingRepository extends PageableRepository<Booking, UUID> {
  @Query("""
    SELECT COUNT(b) > 0
    FROM bookings b
    WHERE b.room_id = :roomId
      AND b.status = 'CONFIRMED'
      AND b.start_time < :endTime
      AND b.end_time > :startTime
  """)
  boolean existsOverlappingBooking(UUID roomId, LocalDateTime startTime, LocalDateTime endTime);

  @Query(
    value = """
      SELECT * FROM bookings
      WHERE (:startTime::timestamp IS NULL OR start_time >= :startTime::timestamp)
        AND (:endTime::timestamp IS NULL OR end_time <= :endTime::timestamp)
        AND (:status IS NULL OR status = :status)
      ORDER BY start_time ASC
    """,
    countQuery = """
      SELECT COUNT(*) FROM bookings
      WHERE (:startTime::timestamp IS NULL OR start_time >= :startTime::timestamp)
        AND (:endTime::timestamp IS NULL OR end_time <= :endTime::timestamp)
        AND (:status IS NULL OR status = :status)
    """
  )
  Page<Booking> findFiltered(
    @Nullable LocalDateTime startTime,
    @Nullable LocalDateTime endTime,
    @Nullable BookingStatus status,
    Pageable bookings
  );
}
