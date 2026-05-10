package com.ticket.booking.booking;

import com.ticket.booking.booking.dto.BookingRequest;
import com.ticket.booking.booking.dto.BookingResponse;
import com.ticket.booking.common.constants.ApiMessage;
import com.ticket.booking.common.entities.BookingStatus;
import com.ticket.booking.ticketCategory.TicketCategoryEntity;
import com.ticket.booking.ticketCategory.TicketCategoryRepository;
import com.ticket.booking.voucher.VoucherEntity;
import com.ticket.booking.voucher.VoucherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.ticket.booking.common.constants.ApiMessage.TICKET_CATEGORY_NOT_FOUND;
import static com.ticket.booking.common.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketCategoryRepository ticketCategoryRepository;

    private final VoucherService voucherService;

    private final BookingMapper bookingMapper;

    public BookingEntity findEntityById(UUID id) {

        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(ApiMessage.BOOKING_NOT_FOUND)
                );

        return booking;
    }

    public BookingResponse getById(UUID id) {

        BookingEntity booking = findEntityById(id);

        return bookingMapper.toResponse(booking);
    }

    //Create booking
    @Transactional
    public BookingResponse create(
            BookingRequest request,
            UUID idempotencyKey
    ) {

        bookingRepository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(e -> {
                    throw new IllegalStateException(BOOKING_DUPLICATED);
                });

        TicketCategoryEntity ticketCategory =
                ticketCategoryRepository.findByIdForUpdate(request.ticketCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(TICKET_CATEGORY_NOT_FOUND)
                        );

        LocalDateTime now = LocalDateTime.now();

        if (ticketCategory.getConcert().getStartTime().isBefore(now)) {
            throw new IllegalStateException(CONCERT_ALREADY_STARTED_OR_ENDED);
        }

        if (ticketCategory.getRemainingQuantity() < request.quantity()) {

            throw new RuntimeException(ApiMessage.OUT_OF_STOCK);
        }

        ticketCategory.setRemainingQuantity(ticketCategory.getRemainingQuantity() - request.quantity());

        BigDecimal subtotal = ticketCategory.getPrice().multiply(
                BigDecimal.valueOf(request.quantity())
        );

        BigDecimal discountAmount = BigDecimal.ZERO;

        VoucherEntity voucher = null;

        if (request.voucherCode() != null
                && !request.voucherCode().isBlank()) {

            voucher = voucherService.validateVoucher(
                    request.voucherCode()
            );

            if (voucher.getExpiredAt().isBefore(now)) {
                throw new IllegalStateException(VOUCHER_EXPIRED);
            }

            discountAmount = subtotal.multiply(
                    voucher.getDiscountPercent()
                            .divide(BigDecimal.valueOf(100))
            );

            voucher.setUsedCount(
                    voucher.getUsedCount() + 1
            );
        }

        BigDecimal totalAmount = subtotal.subtract(discountAmount);

        BookingEntity newBooking = bookingMapper.toBooking(request);
        newBooking.setTicketCategory(ticketCategory);
        newBooking.setIdempotencyKey(idempotencyKey);
        newBooking.setUnitPrice(ticketCategory.getPrice());
        newBooking.setTotalAmount(totalAmount);
        newBooking.setStatus(BookingStatus.PENDING);

        if (voucher != null) {
            newBooking.setVoucher(voucher);
        }

        BookingEntity savedBooking = bookingRepository.save(newBooking);

        return bookingMapper.toResponse(savedBooking);
    }

    public BookingResponse approvedBooking(UUID id) {

        BookingEntity existedBooking = findEntityById(id);
        existedBooking.setStatus(BookingStatus.COMPLETED);

        BookingEntity savedBooking = bookingRepository.save(existedBooking);

        return bookingMapper.toResponse(savedBooking);
    }

    @Transactional
    public BookingResponse cancelBooking(UUID id) {

        BookingEntity booking = findEntityById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException(BOOKING_CANCELLED);
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException(BOOKING_COMPLETED);
        }

        //rollback ticket
        TicketCategoryEntity ticketCategory = booking.getTicketCategory();
        ticketCategory.setRemainingQuantity(
                ticketCategory.getRemainingQuantity() + booking.getQuantity()
        );
        ticketCategoryRepository.save(ticketCategory);

        //rollback voucher
        if (booking.getVoucher() != null) {

            VoucherEntity voucher = booking.getVoucher();

            if (voucher.getUsedCount() > 0) {
                voucher.setUsedCount(voucher.getUsedCount() - 1);
            }
        }

        booking.setStatus(BookingStatus.CANCELLED);

        BookingEntity savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }
}
