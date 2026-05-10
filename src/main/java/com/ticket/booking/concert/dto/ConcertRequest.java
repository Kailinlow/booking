package com.ticket.booking.concert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.ticket.booking.common.constants.ValidationMessage.CONCERT_NAME_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.STARTED_AT_REQUIRED;

@Schema(description = "Request body for creating or updating a concert")
public record ConcertRequest(

        @Schema(description = "Unique identifier of the concert", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotBlank(message = CONCERT_NAME_REQUIRED)
        String name,

        @Schema(description = "Date and time when the concert starts", example = "2024-12-25T19:30:00")
        @NotNull(message = STARTED_AT_REQUIRED)
        LocalDateTime startTime
) {
}
