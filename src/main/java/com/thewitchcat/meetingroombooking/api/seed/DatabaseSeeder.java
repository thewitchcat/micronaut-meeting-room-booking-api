package com.thewitchcat.meetingroombooking.api.seed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Booking;
import com.thewitchcat.meetingroombooking.api.domain.Room;
import com.thewitchcat.meetingroombooking.api.domain.User;
import com.thewitchcat.meetingroombooking.api.enums.BookingStatus;
import com.thewitchcat.meetingroombooking.api.repository.BookingRepository;
import com.thewitchcat.meetingroombooking.api.repository.RoomRepository;
import com.thewitchcat.meetingroombooking.api.repository.UserRepository;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

@Singleton
@Requires(notEnv = "test")
public class DatabaseSeeder {
  
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final BookingRepository bookingRepository;

  public DatabaseSeeder(
    UserRepository userRepository,
    RoomRepository roomRepository,
    BookingRepository bookingRepository
  ) {
    this.userRepository = userRepository;
    this.roomRepository = roomRepository;
    this.bookingRepository = bookingRepository;
  }

  @EventListener
  public void onStartup(StartupEvent event) {
    boolean allEmpty = 
      userRepository.count() == 0 &&
      roomRepository.count() == 0 &&
      bookingRepository.count() == 0;
    
    if (allEmpty) {

      List<User> users = createUsers();
      List<Room> rooms = createRooms();
      List<Booking> bookings = createBookings(users, rooms);
      
      userRepository.saveAll(users);
      roomRepository.saveAll(rooms);
      bookingRepository.saveAll(bookings);

      System.out.println("Database successfully seeded 🌱");
    }
  }

  private List<User> createUsers() {
    return List.of(
      new User(UUID.randomUUID(), "Alice", "alice@example.com"),
      new User(UUID.randomUUID(), "Bob", "bob@example.com"),
      new User(UUID.randomUUID(), "Charlie", "charlie@example.com"),
      new User(UUID.randomUUID(), "David", "david@example.com"),
      new User(UUID.randomUUID(), "Eve", "eve@example.com"),
      new User(UUID.randomUUID(), "Frank", "frank@example.com"),
      new User(UUID.randomUUID(), "Grace", "grace@example.com"),
      new User(UUID.randomUUID(), "Hannah", "hannah@example.com"),
      new User(UUID.randomUUID(), "Ivy", "ivy@example.com"),
      new User(UUID.randomUUID(), "Jack", "jack@example.com"),
      new User(UUID.randomUUID(), "Karen", "karen@example.com"),
      new User(UUID.randomUUID(), "Leo", "leo@example.com"),
      new User(UUID.randomUUID(), "Mona", "mona@example.com"),
      new User(UUID.randomUUID(), "Nina", "nina@example.com"),
      new User(UUID.randomUUID(), "Oscar", "oscar@example.com")
    );
  }

  private List<Room> createRooms() {
    return List.of(
      new Room(UUID.randomUUID(), "Room #1", 10, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #2", 20, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #3", 30, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #4", 40, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #5", 50, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #6", 60, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #7", 70, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #8", 80, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #9", 90, true, LocalDateTime.now()),
      new Room(UUID.randomUUID(), "Room #10", 100, true, LocalDateTime.now())
    );
  }

  private List<Booking> createBookings(List<User> users, List<Room> rooms) {
    UUID aliceId = users.get(0).getId();
    UUID bobId = users.get(1).getId();
    UUID charlieId = users.get(2).getId();
    UUID davidId = users.get(3).getId();

    UUID roomOneId = rooms.get(0).getId();
    UUID roomTwoId = rooms.get(1).getId();

    return List.of(
      createBooking(aliceId, roomOneId, 8, 10),
      createBooking(bobId, roomOneId, 11, 13),
      createBooking(charlieId, roomOneId, 14, 16),
      createBooking(davidId, roomOneId, 19, 21),

      createBooking(aliceId, roomTwoId, 8, 10),
      createBooking(bobId, roomTwoId, 11, 13),

      createBooking(charlieId, roomTwoId, 2, 12, 14),
      createBooking(davidId, roomTwoId, 3, 19, 21)
    );
  }

  private Booking createBooking(UUID userId, UUID roomId, int startHour, int endHour) {
    return createBooking(userId, roomId, 1, startHour, endHour);
  }

  private Booking createBooking(UUID userId, UUID roomId, int day, int startHour, int endHour) {
    return new Booking(
      UUID.randomUUID(),
      LocalDateTime.now().plusDays(day).withHour(startHour).withMinute(0).withSecond(0).withNano(0),
      LocalDateTime.now().plusDays(day).withHour(endHour).withMinute(0).withSecond(0).withNano(0),
      BookingStatus.CONFIRMED,
      LocalDateTime.now(),
      userId,
      roomId
    );
  }
}
