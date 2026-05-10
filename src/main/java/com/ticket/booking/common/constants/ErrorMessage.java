package com.ticket.booking.common.constants;

public final class ErrorMessage {

    private ErrorMessage() {

    }

    public static final String VOUCHER_EXISTED = "Voucher code already existed";

    public static final String BOOKING_CANCELLED = "Booking is already cancelled";

    public static final String BOOKING_COMPLETED = "Cannot cancel a completed booking. Please use refund process instead.";

    public static final String BOOKING_DUPLICATED = "Duplicate booking request";

    public static final String CONCERT_ALREADY_STARTED_OR_ENDED = "Cannot book tickets for a concert that has already started or ended";

    public static final String VOUCHER_EXPIRED = "This voucher has expired";

}
