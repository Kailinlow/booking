package com.ticket.booking.ticketCategory;

import com.ticket.booking.common.constants.ApiMessage;
import com.ticket.booking.common.exception.NotFoundException;
import com.ticket.booking.concert.ConcertEntity;
import com.ticket.booking.concert.ConcertService;
import com.ticket.booking.ticketCategory.dto.TicketCategoryRequest;
import com.ticket.booking.ticketCategory.dto.TicketCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketCategoryService {

    private final TicketCategoryRepository ticketCategoryRepository;

    private final TicketCategoryMapper ticketCategoryMapper;

    private final ConcertService concertService;

    public List<TicketCategoryResponse> getByConcertId(UUID concertId) {

        List<TicketCategoryEntity> ticketCategoryEntityList =
                ticketCategoryRepository.findAllByConcertId(concertId);

        return ticketCategoryMapper.toResponseList(ticketCategoryEntityList);
    }

    public TicketCategoryEntity findEntityById(UUID id) {

        return ticketCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ApiMessage.TICKET_CATEGORY_NOT_FOUND)
                );
    }

    public TicketCategoryResponse getById(UUID id) {
        TicketCategoryEntity ticketCategory = findEntityById(id);

        return ticketCategoryMapper.toTicketCategoryResponse(ticketCategory);
    }

    public TicketCategoryResponse createTicketCategory(TicketCategoryRequest request){

        ConcertEntity concert = concertService.findEntityById(request.concertId());

        TicketCategoryEntity newTicketCategory = ticketCategoryMapper.toTicketCategory(request);
        newTicketCategory.setConcert(concert);
        newTicketCategory.setRemainingQuantity(request.totalQuantity());

        TicketCategoryEntity savedTickedCategory = ticketCategoryRepository.save(newTicketCategory);

        return ticketCategoryMapper.toTicketCategoryResponse(savedTickedCategory);
    }

}
