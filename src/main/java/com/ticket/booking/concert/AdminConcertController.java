package com.ticket.booking.concert;


import com.ticket.booking.common.entities.ApiResponse;
import com.ticket.booking.concert.dto.ConcertRequest;
import com.ticket.booking.concert.dto.ConcertResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/concerts")
@Tag(name = "Admin Concert Management", description = "Administrative APIs for managing concert")
public class AdminConcertController {

    private final ConcertService concertService;

    @Operation(
            summary = "Retrieving all concert",
            description = "Fetch a complete list of all concerts in the system. Restricted to admin users."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ConcertResponse>>> getAllConcerts() {

        List<ConcertResponse> concerts = concertService.getAllConcerts();

        return ResponseEntity.ok(
                ApiResponse.success(concerts)
        );
    }

    @Operation(
            summary = "Create a new Concert",
            description = "Register a new concert event in the system. The concert will be in 'DRAFT' status by default."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<ConcertResponse>> createConcert(
            @Valid @RequestBody ConcertRequest request
    ) {

        ConcertResponse concert = concertService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(concert));
    }

    @Operation(
            summary = "Publish a concert",
            description = "Change the status of a concert to 'PUBLISH' to make it visible to users."
    )
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "The unique UUID of the concert to be publish",
                    example = "123e4567-e89b-12d3-a456-426614174000")
    })
    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<ConcertResponse>> publishConcert(
            @PathVariable UUID id
    ) {

        ConcertResponse concert = concertService.publishConcert(id);

        return ResponseEntity.ok(
                ApiResponse.message("Concert published successfully", concert)
        );
    }
}
