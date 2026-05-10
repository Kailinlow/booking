package com.ticket.booking.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

import static com.ticket.booking.common.constants.ValidationMessage.QUANTITY_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.TICKET_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.QUANTITY_MIN;

@Schema(description = "Request body for initiating a ticket booking")
public record BookingRequest(

        @Schema(description = "The UUID of the selected ticket category", example = "64dae73e-88d8-4553-870a-4e99efbbe6b1")
        @NotNull(message = TICKET_REQUIRED)
        UUID ticketCategoryId,

        @Schema(description = "Number of tickets booked in this transaction", example = "2")
        @NotNull(message = QUANTITY_REQUIRED)
        @Min(value = 1, message = QUANTITY_MIN)
        Integer quantity,

        @Schema(description = "Unique promotional code used at checkout", example = "VOUCHER10")
        String voucherCode
) {
}
