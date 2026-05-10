package com.ticket.booking.concert;

import com.ticket.booking.concert.dto.ConcertRequest;
import com.ticket.booking.concert.dto.ConcertResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcertMapper {
    ConcertEntity toConcert(ConcertRequest request);
    ConcertResponse toConcertResponse(ConcertEntity concert);

    List<ConcertResponse> toResponseList(List<ConcertEntity> concertEntityList);
}
