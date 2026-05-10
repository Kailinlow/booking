package com.ticket.booking.ticketCategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Response object containing ticket category details")
public record TicketCategoryResponse(

        @Schema(description = "Unique identifier of the ticket category", example = "64dae73e-88d8-4553-870a-4e99efbbe6b1")
        UUID id,

        @Schema(description = "Title of the ticket category", example = "STANDARD")
        String name,

        @Schema(description = "The unit price for a single ticket in this category", example = "1500000.00")
        BigDecimal price,

        @Schema(description = "Maximum number of tickets initially released for this category", example = "100")
        Integer totalQuantity,

        @Schema(description = "Number of tickets currently available for purchase", example = "24")
        Integer remainingQuantity
) {
}
