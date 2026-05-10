package com.ticket.booking.ticketCategory;

import com.ticket.booking.common.entities.ApiResponse;
import com.ticket.booking.ticketCategory.dto.TicketCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket-categories")
@Tag(name = "Ticket Category Management", description = "Endpoints for browsing ticket types and availability")
public class TicketCategoryController {

    private final TicketCategoryService ticketCategoryService;

    @Operation(
            summary = "Get ticket categories by concert",
            description = "Retrieves all available ticket categories (e.g., VIP, Standard) for a specific concert event."
    )
    @Parameters({
            @Parameter(
                    name = "concertId",
                    description = "The unique UUID of the concert",
                    example = "123e4567-e89b-12d3-a456-426614174000")
    })
    @GetMapping("/concert/{concertId}")
    public ResponseEntity<ApiResponse<List<TicketCategoryResponse>>> getByConcertId(
            @PathVariable UUID concertId
            ) {

        List<TicketCategoryResponse> responses = ticketCategoryService.getByConcertId(concertId);

        return ResponseEntity.ok(
                ApiResponse.success(responses)
        );
    }
}
