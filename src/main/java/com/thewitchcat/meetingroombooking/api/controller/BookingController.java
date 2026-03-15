package com.thewitchcat.meetingroombooking.api.controller;

import java.util.List;
import java.util.UUID;

import com.thewitchcat.meetingroombooking.api.domain.Booking;
import com.thewitchcat.meetingroombooking.api.dto.ErrorResponseDto;
import com.thewitchcat.meetingroombooking.api.dto.PagedResponseDto;
import com.thewitchcat.meetingroombooking.api.dto.booking.BookingCancelResponseDto;
import com.thewitchcat.meetingroombooking.api.dto.booking.BookingRequestDto;
import com.thewitchcat.meetingroombooking.api.dto.booking.BookingResponseDto;
import com.thewitchcat.meetingroombooking.api.service.BookingService;

import io.micronaut.data.model.Page;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller("/bookings")
@Tag(name = "Booking", description = "Operations related to bookings")
@ExecuteOn(TaskExecutors.BLOCKING)
public class BookingController {
  
  private final BookingService service;

  public BookingController(BookingService service) {
    this.service = service;
  }

  @Get
  @ApiResponse(responseCode = "200", description = "List of Bookings")
  @Operation(summary = "List Bookings", description = "List registered Bookings on the system")
  public PagedResponseDto<BookingResponseDto> getAllBookings(int page, int size) {
    Page<Booking> bookings = service.getAllBookings(page, size);

    List<BookingResponseDto> res = bookings.getContent().stream()
      .map(booking -> new BookingResponseDto(
        booking.getId(),
        booking.getStartTime(),
        booking.getEndTime(),
        booking.getStatus(),
        booking.getCreatedAt(),
        booking.getUser(),
        booking.getRoom()
      )).toList();

    return new PagedResponseDto<>(
      res,
      page,
      size,
      bookings.getTotalSize()
    );
  }
  

  @Post
  // @ApiResponse(responseCode = "404", description = "User not found")
  // @ApiResponse(responseCode = "404", description = "Room not found")
  // @ApiResponse(responseCode = "403", description = "Room is inactive")
  @Operation(summary = "Create Booking", description = "Book a Room")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Successfully booked a room",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = BookingResponseDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "Room already booked within the time range",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorResponseDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Booking not found",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorResponseDto.class)
      )
    )
  })
  public HttpResponse<BookingResponseDto> createBooking(@Valid @Body BookingRequestDto payload) {

    Booking booking = service.createBooking(payload);

    BookingResponseDto res = new BookingResponseDto(
      booking.getId(),
      booking.getStartTime(),
      booking.getEndTime(),
      booking.getStatus(),
      booking.getCreatedAt(),
      booking.getUser(),
      booking.getRoom()
    );

    return HttpResponse.created(res);
  }

  @Post("/{bookingId}/cancel")
  @ApiResponse(responseCode = "200", description = "Successfully cancelled a booked Room")
  @ApiResponse(responseCode = "404", description = "Booking not found")
  @Operation(summary = "Cancel Booking", description = "Cancel a Booking")
  public HttpResponse<BookingCancelResponseDto> cancelBooking(@PathVariable UUID bookingId) {

    Booking bookingEntity = service.cancelBooking(bookingId);
    BookingResponseDto bookingResponse = new BookingResponseDto(
      bookingEntity.getId(),
      bookingEntity.getStartTime(),
      bookingEntity.getEndTime(),
      bookingEntity.getStatus(),
      bookingEntity.getCreatedAt(),
      bookingEntity.getUser(),
      bookingEntity.getRoom()
    );

    BookingCancelResponseDto res = new BookingCancelResponseDto(
      "Booking successfully cancelled.",
      bookingResponse
    );

    return HttpResponse.ok(res);
  }
}
