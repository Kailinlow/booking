package com.ticket.booking.ticketCategory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

import static com.ticket.booking.common.constants.ValidationMessage.CONCERT_ID_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.TICKET_CATEGORY_NAME_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.TICKET_PRICE_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.TICKET_PRICE_MIN;
import static com.ticket.booking.common.constants.ValidationMessage.TOTAL_QUANTITY_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.TOTAL_QUANTITY_MIN;

public record TicketCategoryRequest(

        @NotNull(message = CONCERT_ID_REQUIRED)
        UUID concertId,

        @NotBlank(message = TICKET_CATEGORY_NAME_REQUIRED)
        String name,

        @NotNull(message = TICKET_PRICE_REQUIRED)
        @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = TICKET_PRICE_MIN
        )
        BigDecimal price,

        @NotNull(message = TOTAL_QUANTITY_REQUIRED)
        @Min(
            value = 1,
            message = TOTAL_QUANTITY_MIN
        )
        Integer totalQuantity
) {
}
