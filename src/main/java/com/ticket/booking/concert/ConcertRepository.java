package com.ticket.booking.concert;

import com.ticket.booking.common.entities.ConcertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConcertRepository extends JpaRepository<ConcertEntity, UUID> {
    List<ConcertEntity> findAllByStatus(ConcertStatus status);

}
