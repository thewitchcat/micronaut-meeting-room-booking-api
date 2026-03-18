package com.thewitchcat.meetingroombooking.api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Booking;
import com.thewitchcat.meetingroombooking.api.domain.Room;
import com.thewitchcat.meetingroombooking.api.domain.User;
import com.thewitchcat.meetingroombooking.api.dto.booking.BookingFilterRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.booking.BookingRequestDto;
import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;
import com.thewitchcat.meetingroombooking.api.exception.booking.BookingConflictException;
import com.thewitchcat.meetingroombooking.api.exception.booking.BookingNotFoundException;
import com.thewitchcat.meetingroombooking.api.exception.room.RoomInactiveException;
import com.thewitchcat.meetingroombooking.api.exception.room.RoomNotFoundException;
import com.thewitchcat.meetingroombooking.api.exception.user.UserNotFoundException;
import com.thewitchcat.meetingroombooking.api.repository.BookingRepository;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;
import com.thewitchcat.meetingroombooking.api.repository.UserRepository;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

@Singleton
public class BookingService {
  
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final BookingRepository bookingRepository;

  public BookingService(
    UserRepository userRepository,
    RoomRepository roomRepository,
    BookingRepository bookingRepository
  ) {
    this.userRepository = userRepository;
    this.roomRepository = roomRepository;
    this.bookingRepository = bookingRepository;
  }

  @Transactional
  public Booking createBooking(BookingRequestDto payload) {

    if (payload.startTime().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Start time can't be in the past!");
    }

    if (!payload.startTime().isBefore(payload.endTime())) {
      throw new IllegalArgumentException("Start time must be before end time!");
    }

    Room room = roomRepository.findById(payload.roomId())
      .orElseThrow(() -> new RoomNotFoundException());

    if (!room.isActive()) {
      throw new RoomInactiveException();
    }

    boolean conflict = bookingRepository.existsOverlappingBooking(
      room.getId(), 
      payload.startTime(), 
      payload.endTime()
    );

    if (conflict) {
      throw new BookingConflictException();
    }

    User user = userRepository.findById(payload.userId())
      .orElseThrow(() -> new UserNotFoundException());

    Booking booking = new Booking(
      UUID.randomUUID(),
      payload.startTime(),
      payload.endTime(),
      BookingStatus.CONFIRMED,
      LocalDateTime.now(),
      user.getId(),
      room.getId()
    );

    bookingRepository.save(booking);

    return booking;
  }

  @Transactional
  public Booking cancelBooking(UUID bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
      .orElseThrow(() -> new BookingNotFoundException());

    if (booking.getEndTime().isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Can't cancel a finished booking.");
    }

    if (booking.getStatus() == BookingStatus.CANCELLED) {
      throw new IllegalStateException("Booking is already cancelled. Can't cancel the already cancelled booking.");
    }

    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.update(booking);

    return booking;
  }

  public Page<Booking> getAllBookings(BookingFilterRequestDto filter, int page, int size) {
    Pageable bookings = Pageable.from(page, size);
    return bookingRepository.findFiltered(
      filter.startTime(),
      filter.endTime(),
      filter.status(),
      bookings
    );
  }
}
