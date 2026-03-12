# 📌 Meeting Room Booking System

A REST API for managing meeting room reservations built with Micronaut.

This project demonstrates clean service-layer architecture, enforcement of real-world booking rules (such as time conflict detection), transactional consistency, and structured API error responses.

---

## Overview

This application allows:

- Create users
- Create and manage meeting rooms
- Book rooms for specific time ranges
- Prevent booking conflicts
- Cancel bookings with proper lifecycle validation

The goal of this project is to demonstrate:

- Validations
- Service-layer business logic
- Transactional boundaries
- Clean exception handling
- Proper API error responses

---

## Tech Stack

- Java 21
- Micronaut 4.10.9
- Micronaut Data JDBC (using JDBC directly instead of JPA/Hibernate for a lightweight, DTO-driven approach)
- Flyway
- PostgreSQL 17
- Gradle

---

## Project Structure

The project follows a layered architecture:
```
src/main/java/...  
├── controller      # HTTP endpoints  
├── domain          # Domain entities and enums  
├── dto             # API request and response objects  
├── enums           # Predefined constant values
├── exception       # Custom domain exceptions and error handling  
├── repository      # Data access using Micronaut Data  
└── service         # Business logic and transactional boundaries         
```
---

## Domain Model

### User

Represents a person who can create bookings.

### Room

Represents a meeting room that can be active or inactive.

### Booking

Represents a time-based reservation of a room by a user.

A Booking includes:

- `startTime`
- `endTime`
- `status` (`CONFIRMED`, `CANCELLED`)
- `createdAt`
- associated `User`
- associated `Room`

---

## Implemented Business Rules

This project enforces the following rules:

### Booking Creation

- Start time must be before end time
- Start time cannot be in the past
- User must exist
- Room must exist
- Room must be active
- Booking is created with status `CONFIRMED`
- The booking time range must not overlap with existing bookings for the same room

Two bookings are considered overlapping if:

`existing.startTime < new.endTime AND existing.endTime > new.startTime`

### Booking Cancellation

- Booking must exist
- Booking cannot already be cancelled
- Booking cannot be cancelled if it has already finished
- Cancellation changes status to `CANCELLED` (no hard delete)

---

## Example API Usage

### Request

`POST /bookings`

```json
{
  "userId": "88ded768-ca83-4999-953d-85f049f52c7c",
  "roomId": "95ef6de0-117c-4c26-9030-8c0a9234c3ae",
  "startTime": "2026-03-11T10:00:00",
  "endTime": "2026-03-11T11:00:00"
}
````

### Success Response

```json
{
  "id": "16b3770a-ecd9-4e0a-aeb6-a63d86fd61d9",
  "status": "CONFIRMED",
  "startTime": "2026-03-11T10:00:00",
  "endTime": "2026-03-11T11:00:00"
}
```

---

## API Documentation

The API is documented using OpenAPI with Scalar UI.

Detailed API documentation is available at:

`http://localhost:3000/scalar`

---

## Error Handling

The application uses:

* Custom domain exceptions (e.g., `UserNotFoundException`, `BookingConflictException`)
* Global exception handling using Micronaut's `ExceptionHandler` interface
* Proper HTTP status codes:

  * `404 NOT FOUND`
  * `409 CONFLICT`
  * `403 FORBIDDEN`

All error responses follow a standardized `ErrorResponseDto` structure:

* `error`: A short error identifier or code
* `message`: A human-readable description of the error

### Example Error Response

```json
{
  "error": "BOOKING_CONFLICT",
  "message": "The room is already booked for the selected time range."
}
```

---

## Running the Application

Start the database and run the application:

```bash
./gradlew run
```

The application runs on port 3000 (default is 8080 in Micronaut).

If you want to reset the database schema, uncomment `clean-schema: true` in `application.yaml` before starting the application.  

This is **optional** — integration and unit tests already manage the database state with `@BeforeEach` and `@AfterEach`. You only need this if you want to start with a clean database while running the application against PostgreSQL.

---

## Testing

Integration tests are implemented using:

- Micronaut Test
- Rest-Assured

Run the test suite with:

```bash
./gradlew test
```

---

## Transaction Management

- Service layer methods are annotated with `@Transactional`.
- Booking creation and cancellation operations are executed atomically.

---

## Design Decisions

Some implementation choices were made intentionally to emphasize clean backend architecture:

- **Service Layer Business Logic**  
  Business rules such as booking conflict detection and cancellation validation are implemented in the service layer rather than controllers.

- **Soft Cancellation**  
  Bookings are cancelled by updating their status instead of deleting records, preserving historical data.

- **Structured Error Responses**  
  All errors follow a consistent `ErrorResponseDto` schema to ensure predictable API behavior for clients.

- **Transactional Boundaries**  
  Critical operations such as booking creation and cancellation are wrapped in transactions to ensure data consistency.
  
- **Concurrency Considerations**  
  Conflict detection is enforced at the service layer. In production systems, additional database-level safeguards (such as unique time constraints or locking strategies) may be required to prevent race conditions.

- **Choice of Micronaut Data JDBC over JPA/Hibernate**  
  This project uses Micronaut Data JDBC rather than traditional JPA/Hibernate. The goal is to keep the application lightweight and maintain a clear separation between the domain model and database representation.  
  Entities are not treated as the source of truth; DTOs are used for API responses and requests. This approach emphasizes **service-layer business logic** and reduces unnecessary coupling that full ORM frameworks can introduce. While Spring Boot and JPA/Hibernate are common in Java ecosystems, this choice allows for simpler, more explicit database interactions, aligning with the project’s focus on **clean architecture and transactional control**.

---

## Future Improvements

* Role-based authorization (Admin/User)
* Advanced booking status lifecycle
* Time zone support
* Pagination and filtering endpoints