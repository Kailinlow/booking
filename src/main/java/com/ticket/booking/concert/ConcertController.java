package com.ticket.booking.concert;

import com.ticket.booking.common.entities.ApiResponse;
import com.ticket.booking.concert.dto.ConcertResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
@Tag(name = "Concert Management", description = "Endpoints for retrieving and managing concert information")
public class ConcertController {

    private final ConcertService concertService;

    @Operation(
            summary = "Get All Publish Concert",
            description = "Browse all concerts that are currently set to 'Publish' status."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ConcertResponse>>> getPublishedConcerts() {

        List<ConcertResponse> concerts = concertService.getPublishedConcerts();

        return ResponseEntity.ok(
                ApiResponse.success(concerts)
        );
    }

    @Operation(
            summary = "Get Concert details by ID",
            description = "Fetch detailed information specific concert"
    )
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "The unique UUID of the concert",
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConcertResponse>> getConcertById(
            @PathVariable UUID id
    ) {

        ConcertResponse concert = concertService.getConcertById(id);

        return ResponseEntity.ok(
                ApiResponse.success(concert)
        );
    }
}
