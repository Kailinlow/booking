package com.ticket.booking.concert.dto;

import com.ticket.booking.common.entities.ConcertStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response object containing concert details")
public record ConcertResponse(

        @Schema(description = "Unique identifier of the concert", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Official title of the concert event", example = "Music Festival 2026")
        String name,

        @Schema(description = "Date and time when the concert starts", example = "2024-12-25T19:30:00")
        LocalDateTime startTime,

        @Schema(description = "Status of the concert", example = "DRAFT/PUBLISH/CANCELLED")
        ConcertStatus status
) {
}
