package com.ticket.booking.ticketCategory;

import com.ticket.booking.common.entities.ApiResponse;
import com.ticket.booking.ticketCategory.dto.TicketCategoryRequest;
import com.ticket.booking.ticketCategory.dto.TicketCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/ticket-categories")
@Tag(name = "Admin Ticket Category Management", description = "Administrative endpoints for configuring ticket types, pricing, and inventory")
public class AdminTicketCategoryController {

    private final TicketCategoryService ticketCategoryService;

    @Operation(
            summary = "Create a new ticket category",
            description = "Defines a new ticket type for a concert, including its price and total allocation."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<TicketCategoryResponse>> create(
            @Valid @RequestBody TicketCategoryRequest request
            ) {

        TicketCategoryResponse response = ticketCategoryService.createTicketCategory(request);

        return ResponseEntity.ok(
                ApiResponse.created(response)
        );
    }
}
