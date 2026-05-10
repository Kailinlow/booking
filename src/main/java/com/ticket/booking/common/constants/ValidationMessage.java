package com.ticket.booking.common.constants;

public final class ValidationMessage {

    private ValidationMessage() {
    }

    //Concert
    public static final String CONCERT_NAME_REQUIRED = "Concert name is requires";

    public static final String STARTED_AT_REQUIRED = "Started at is required";

    // Ticket category
    public static final String CONCERT_ID_REQUIRED = "Concert id is required";

    public static final String TICKET_CATEGORY_NAME_REQUIRED = "Ticket category name is required";

    public static final String TICKET_PRICE_REQUIRED = "Ticket price is required";

    public static final String TICKET_PRICE_MIN = "Ticket price must be greater than 0";

    public static final String TOTAL_QUANTITY_REQUIRED = "Total quantity is required";

    public static final String TOTAL_QUANTITY_MIN = "Total quantity must be greater than 0";

    // Voucher
    public static final String VOUCHER_CODE_REQUIRED = "Voucher code is required";

    public static final String DISCOUNT_PERCENT_REQUIRED = "Discount percent is required";

    public static final String DISCOUNT_PERCENT_MIN = "Discount percent must be greater than 0";

    public static final String DISCOUNT_PERCENT_MAX = "Discount percent cannot exceed 100";

    public static final String MAX_USAGE_REQUIRED = "Max usage is required";

    public static final String MAX_USAGE_MIN = "Max usage must be greater than 0";

    public static final String EXPIRED_AT_REQUIRED = "Expired at is required";

    //Booking
    public static final String TICKET_REQUIRED = "Ticket category is required";

    public static final String QUANTITY_REQUIRED = "Quantity is required";

    public static final String QUANTITY_MIN = "Quantity must be greater than 0";
}
