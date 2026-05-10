package com.ticket.booking.booking;

import com.ticket.booking.booking.dto.BookingRequest;
import com.ticket.booking.booking.dto.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingEntity toBooking(BookingRequest request);

    @Mapping(
            target = "ticketCategoryId",
            source = "ticketCategory.id"
    )
    @Mapping(
            target = "voucherId",
            source = "voucher.id"
    )
    BookingResponse toResponse(BookingEntity booking);
}
