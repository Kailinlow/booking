package com.ticket.booking.common.constants;

public final class ApiMessage {

    private ApiMessage() {
    }

    public static final String SUCCESS = "Success";
    public static final String CREATED = "Created successfully";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public static final String BOOKING_CREATED = "Booking created successfully";
    public static final String BOOKING_NOT_FOUND = "Booking not found";
    public static final String OUT_OF_STOCK = "Ticket is out of stock";

    public static final String TICKET_CATEGORY_NOT_FOUND = "Ticket category not found";


    public static final String VOUCHER_INVALID = "Voucher is invalid";
    public static final String VOUCHER_EXPIRED = "Voucher has expired";
    public static final String VOUCHER_USAGE_EXCEEDED = "Voucher usage limit exceeded";

    public static final String CONCERT_NOT_FOUND = "Concert not found";

    public static final String INVALID_REQUEST = "Invalid request";
}
