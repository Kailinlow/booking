package com.ticket.booking;

import com.ticket.booking.booking.BookingService;
import com.ticket.booking.booking.dto.BookingRequest;
import com.ticket.booking.common.entities.ConcertStatus;
import com.ticket.booking.concert.ConcertEntity;
import com.ticket.booking.concert.ConcertRepository;
import com.ticket.booking.ticketCategory.TicketCategoryEntity;
import com.ticket.booking.ticketCategory.TicketCategoryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ticket.booking.common.constants.ErrorMessage.BOOKING_DUPLICATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookingConcurrencyIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private EntityManager entityManager;

    private UUID ticketCategoryId;

    @BeforeEach
    void setup() {
        ConcertEntity concert = new ConcertEntity();

        concert.setName("Test Concert");
        concert.setStartTime(LocalDateTime.now().plusDays(1));
        concert.setStatus(ConcertStatus.PUBLISHED);

        concert = concertRepository.saveAndFlush(concert);

        entityManager.clear();

        TicketCategoryEntity ticketCategory = new TicketCategoryEntity();

        ticketCategory.setName("VIP");
        ticketCategory.setPrice(BigDecimal.valueOf(100000));
        ticketCategory.setTotalQuantity(1);
        ticketCategory.setRemainingQuantity(1);

        ConcertEntity managedConcert = concertRepository.findById(concert.getId()).orElseThrow();
        ticketCategory.setConcert(managedConcert);

        ticketCategory = ticketCategoryRepository.saveAndFlush(ticketCategory);

        ticketCategoryId = ticketCategory.getId();

        entityManager.clear();
    }

    @Test
    void shouldPreventOverSelling() throws Exception {

        int threadCount = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();

        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {

            executorService.submit(() -> {

                try {

                    BookingRequest request= new BookingRequest(
                            ticketCategoryId,
                            1,
                            null
                    );

                    bookingService.create(request, UUID.randomUUID());

                    successCount.incrementAndGet();
                } catch (Exception e) {

                    e.printStackTrace();
                    failCount.incrementAndGet();
                } finally {

                    latch.countDown();
                }
            });
        }

        latch.await();

        assertEquals(1, successCount.get());

        assertEquals(1, failCount.get());
    }

    @Test
    void shouldHandleDoubleClickIdempotency() throws Exception {

        TicketCategoryEntity category = ticketCategoryRepository.findById(ticketCategoryId).get();
        category.setRemainingQuantity(100);

        ticketCategoryRepository.saveAndFlush(category);

        int threadCount = 3;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();

        AtomicInteger duplicateCount = new AtomicInteger();

        UUID sharedIdempotencyKey = UUID.randomUUID();

        for (int i = 0; i < threadCount; i++) {

            executorService.submit(() -> {
                try {

                    BookingRequest request = new BookingRequest(ticketCategoryId, 1, null);

                    bookingService.create(request, sharedIdempotencyKey);

                    successCount.incrementAndGet();
                } catch (IllegalStateException e) {

                    if (e.getMessage().contains(BOOKING_DUPLICATED)) {

                        duplicateCount.incrementAndGet();
                    }
                } catch (DataIntegrityViolationException e) {

                    duplicateCount.incrementAndGet();
                } catch (Exception ex) {

                    ex.printStackTrace();
                    duplicateCount.incrementAndGet();
                } finally {

                    latch.countDown();
                }
            });
        }

        latch.await();

        assertEquals(1, successCount.get());
        assertEquals(2, duplicateCount.get());
    }
}
