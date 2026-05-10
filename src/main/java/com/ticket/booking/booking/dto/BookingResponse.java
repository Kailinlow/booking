package com.ticket.booking.booking.dto;

import com.ticket.booking.common.entities.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Response object representing a booking transaction")
public record BookingResponse(

        @Schema(description = "Unique identifier of the booking (Booking Reference)", example = "b9e1a2c3-d4e5-4f6a-7b8c-9d0e1f2a3b4c")
        UUID id,

        @Schema(description = "The UUID of the selected ticket category", example = "64dae73e-88d8-4553-870a-4e99efbbe6b1")
        UUID ticketCategoryId,

        @Schema(description = "The UUID of the applied voucher (null if no voucher used)", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ab")
        UUID voucherId,

        @Schema(description = "Number of tickets booked in this transaction", example = "2")
        Integer quantity,

        @Schema(description = "Final price after applying discounts and taxes", example = "2500000.00")
        BigDecimal totalAmount,

        @Schema(description = "Current lifecycle status of the booking", example = "PENDING")
        BookingStatus status
) {
}
