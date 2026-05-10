package com.ticket.booking.ticketCategory;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketCategoryRepository extends JpaRepository<TicketCategoryEntity, UUID> {
    List<TicketCategoryEntity> findAllByConcertId(UUID concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT t
        FROM TicketCategoryEntity t
        WHERE t.id = :id
    """)
    Optional<TicketCategoryEntity> findByIdForUpdate(
            @Param("id") UUID id
    );

    @Query("SELECT t.concert.startTime FROM TicketCategoryEntity t WHERE t.id = :id")
    LocalDateTime getConcertStartTime(@Param("id") UUID ticketCategoryId);
}
