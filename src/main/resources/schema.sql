-- =====================================
-- CONCERTS
-- =====================================

CREATE TABLE IF NOT EXISTS concerts (

    id UUID PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    start_time TIMESTAMP NOT NULL,

    status VARCHAR(50) NOT NULL
);

-- =====================================
-- TICKET CATEGORIES
-- =====================================

CREATE TABLE IF NOT EXISTS ticket_categories (

    id UUID PRIMARY KEY,

    concert_id UUID NOT NULL,

    name VARCHAR(100) NOT NULL,

    price NUMERIC(12,2) NOT NULL,

    total_quantity INT NOT NULL,

    remaining_quantity INT NOT NULL,

    CONSTRAINT fk_ticket_category_concert
        FOREIGN KEY (concert_id)
        REFERENCES concerts(id)
);

-- =====================================
-- VOUCHERS
-- =====================================

CREATE TABLE IF NOT EXISTS vouchers (

    id UUID PRIMARY KEY,

    code VARCHAR(100) NOT NULL UNIQUE,

    discount_percent NUMERIC(5,2) NOT NULL,

    max_usage INT NOT NULL,

    used_count INT NOT NULL DEFAULT 0,

    expired_at TIMESTAMP NOT NULL
);

-- =====================================
-- BOOKINGS
-- =====================================

CREATE TABLE IF NOT EXISTS bookings (

    id UUID PRIMARY KEY,

    ticket_category_id UUID NOT NULL,

    quantity INT NOT NULL,

    unit_price NUMERIC(12,2) NOT NULL,

    voucher_id UUID,

    voucher_code VARCHAR(100),

    discount_percent NUMERIC(5,2),

    discount_amount NUMERIC(12,2),

    total_amount NUMERIC(12,2) NOT NULL,

    status VARCHAR(50) NOT NULL,

    idempotency_key UUID NOT NULL UNIQUE,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_booking_ticket_category
        FOREIGN KEY (ticket_category_id)
        REFERENCES ticket_categories(id),

    CONSTRAINT fk_booking_voucher
        FOREIGN KEY (voucher_id)
        REFERENCES vouchers(id)
);

-- =====================================
-- INDEXES
-- =====================================

CREATE INDEX IF NOT EXISTS idx_booking_idempotency
ON bookings(idempotency_key);

CREATE INDEX IF NOT EXISTS idx_booking_ticket_category
ON bookings(ticket_category_id);

CREATE INDEX IF NOT EXISTS idx_ticket_category_concert
ON ticket_categories(concert_id);

CREATE INDEX IF NOT EXISTS idx_voucher_code
ON vouchers(code);