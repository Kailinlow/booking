package com.ticket.booking.booking;

import com.ticket.booking.booking.dto.BookingRequest;
import com.ticket.booking.booking.dto.BookingResponse;
import com.ticket.booking.common.entities.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@Tag(name = "Booking Management", description = "Endpoints for handling ticket reservations and payment processing")
public class BookingController {

    private final BookingService bookingService;

    @Operation(
            summary = "Get booking details",
            description = "Retrieve comprehensive information about a specific booking using its UUID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getById(
            @PathVariable UUID id
    ) {

        BookingResponse response =
                bookingService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    @Operation(
            summary = "Create a new booking",
            description = "Initiates a ticket reservation. Requires an Idempotency-Key to prevent duplicate transactions."
    )
    @Parameters({
            @Parameter(
                    name = "Idempotency-Key",
                    in = ParameterIn.HEADER,
                    description = "Unique key to ensure the request is processed only once",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> create(
            @Valid @RequestBody BookingRequest request,
            @RequestHeader("Idempotency-Key") UUID idempotencyKey
            ) {

        BookingResponse response = bookingService.create(request, idempotencyKey);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @Operation(
            summary = "Complete a booking",
            description = "Finalize the booking process, typically triggered after a successful payment verification."
    )
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "The unique UUID of the booking to be completed",
                    required = true,
                    example = "b9e1a2c3-d4e5-4f6a-7b8c-9d0e1f2a3b4c"
            )
    })
    @PatchMapping("/{id}/completed")
    public ResponseEntity<ApiResponse<BookingResponse>> approvedBooking(
            @PathVariable UUID id
    ) {

        BookingResponse response = bookingService.approvedBooking(id);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    @Operation(
            summary = "Cancel a booking",
            description = "Cancels a pending or confirmed booking and returns the tickets to the inventory."
    )
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "The unique UUID of the booking to be cancelled",
                    required = true,
                    example = "b9e1a2c3-d4e5-4f6a-7b8c-9d0e1f2a3b4c"
            )
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable UUID id
    ) {
        BookingResponse response = bookingService.cancelBooking(id);
        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }
}
