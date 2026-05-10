package com.ticket.booking.concert;

import com.ticket.booking.common.constants.ApiMessage;
import com.ticket.booking.common.entities.ConcertStatus;
import com.ticket.booking.common.exception.NotFoundException;
import com.ticket.booking.concert.dto.ConcertRequest;
import com.ticket.booking.concert.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    public List<ConcertResponse> getAllConcerts() {
        List<ConcertEntity> concertEntityList = concertRepository.findAll();

        return concertMapper.toResponseList(concertEntityList);
    }

    public List<ConcertResponse> getPublishedConcerts() {
        List<ConcertEntity> concertEntityList = concertRepository.findAllByStatus(ConcertStatus.PUBLISHED);

        return concertMapper.toResponseList(concertEntityList);
    }

    public ConcertResponse createConcert(ConcertRequest request) {
        ConcertEntity newConcert = concertMapper.toConcert(request);
        newConcert.setStatus(ConcertStatus.DRAFT);

        ConcertEntity savedConcert = concertRepository.save(newConcert);

        return concertMapper.toConcertResponse(savedConcert);
    }

    public ConcertEntity findEntityById(UUID id) {
        return concertRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ApiMessage.CONCERT_NOT_FOUND)
                );
    }

    public ConcertResponse getConcertById(UUID id) {
        ConcertEntity concert = findEntityById(id);

        return concertMapper.toConcertResponse(concert);
    }

    public ConcertResponse publishConcert(UUID id) {
        ConcertEntity concert = findEntityById(id);

        concert.setStatus(ConcertStatus.PUBLISHED);

        concertRepository.save(concert);

        return concertMapper.toConcertResponse(concert);
    }
}
