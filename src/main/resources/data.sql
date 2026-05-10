-- =====================================
-- SAMPLE CONCERT
-- =====================================

INSERT INTO concerts (
    id,
    name,
    start_time,
    status
)
VALUES (
    '123e4567-e89b-12d3-a456-426614174000,
    'Music Festival 2026',
    '2026-12-25 19:30:00',
    'PUBLISHED'
);

-- =====================================
-- SAMPLE TICKET CATEGORIES
-- =====================================

INSERT INTO ticket_categories (
    id,
    concert_id,
    name,
    price,
    total_quantity,
    remaining_quantity
)
VALUES
(
    '64dae73e-88d8-4553-870a-4e99efbbe6b0',
    '123e4567-e89b-12d3-a456-426614174000',
    'VIP',
    5000000,
    10,
    10
),
(
    '64dae73e-88d8-4553-870a-4e99efbbe6b1',
    '123e4567-e89b-12d3-a456-426614174000',
    'STANDARD',
    1500000.00,
    100,
    24
);

-- =====================================
-- SAMPLE VOUCHER
-- =====================================

INSERT INTO vouchers (
    id,
    code,
    discount_percent,
    max_usage,
    used_count,
    expired_at
)
VALUES (
    'a1b2c3d4-e5f6-7890-abcd-1234567890ab',
    'VOUCHER10',
    15.5,
    100,
    24,
    '2027-12-31 23:59:59'
);