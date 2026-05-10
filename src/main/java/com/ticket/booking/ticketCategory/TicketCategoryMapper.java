package com.ticket.booking.ticketCategory;

import com.ticket.booking.ticketCategory.dto.TicketCategoryRequest;
import com.ticket.booking.ticketCategory.dto.TicketCategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketCategoryMapper {
    TicketCategoryEntity toTicketCategory(TicketCategoryRequest request);
    TicketCategoryResponse toTicketCategoryResponse(TicketCategoryEntity ticketCategoryEntity);

    List<TicketCategoryResponse> toResponseList(List<TicketCategoryEntity> ticketCategoryEntityList);
}
