# Event Ticket Booking System

A backend system for managing event ticket bookings with support for:

- Concert management
- Ticket category management
- Voucher system
- Concurrency-safe booking flow
- Overselling prevention
- Idempotency handling

Built with Java Spring Boot and PostgreSQL.

---

# Features

## Concert Management
- Create concerts
- Publish concerts
- View available concerts

## Ticket Category Management
- Create ticket categories
- Manage ticket inventory
- Track remaining ticket quantity

## Voucher System
- Create discount vouchers
- Validate voucher expiration
- Enforce voucher usage limits

## Booking System
- Create bookings
- Prevent overselling during concurrent requests
- Apply vouchers to bookings
- Handle duplicate booking retries using idempotency keys

---

# Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- OpenAPI

---

# System Design Highlights

## Transaction Management

Booking creation uses database transactions to ensure:

- Ticket inventory updates
- Voucher usage updates
- Booking creation

are processed atomically.

If any step fails, the entire transaction is rolled back.

---

## Overselling Prevention

Pessimistic locking is used during booking creation to prevent overselling when multiple users attempt to purchase the same ticket simultaneously.

Example:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

This ensures inventory consistency under concurrent booking requests.

---

## Idempotency Handling

The booking API supports idempotency keys to prevent duplicate bookings caused by:

- Client retries
- Network failures
- Double-click actions

Each booking request must include:

```http
Idempotency-Key: <uuid>
```

---

# Database Design

Main entities:

- Concerts
- Ticket Categories
- Vouchers
- Bookings

Relationships:

- One concert can have multiple ticket categories
- One voucher can be used by multiple bookings
- One booking can purchase only one ticket category

---

# API Endpoints

## Concert APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/concerts` | Create concert |
| GET | `/api/v1/concerts` | Get published concerts |
| GET | `/api/v1/concerts/{id}` | Get concert detail |
| PATCH | `/api/v1/concerts/{id}/publish` | Publish concert |

---

## Ticket Category APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/ticket-categories` | Create ticket category |
| GET | `/api/v1/ticket-categories/concert/{concertId}` | Get ticket categories by concert |

---

## Voucher APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/vouchers` | Create voucher |

---

## Booking APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/bookings` | Create booking |
| GET | `/api/v1/bookings/{id}` | Get booking detail |

---

# Booking Flow

Booking flow implementation:

1. Validate idempotency key
2. Lock ticket inventory row
3. Validate remaining ticket quantity
4. Deduct inventory
5. Validate voucher
6. Increment voucher usage count
7. Calculate total amount
8. Create booking
9. Commit transaction

---

# Swagger API Documentation

Swagger UI:

```text
/swagger-ui/index.html
```

---

# Running the Project

## Prerequisites

- Java 21
- PostgreSQL
- Maven

---

## Clone project

```bash
git clone <repository-url>
```

---

## Configure database

Update:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/concert_booking
spring.datasource.username=postgres
spring.datasource.password=postgres
```

---

## Run application

```bash
./mvnw spring-boot:run
```

---

# Example Booking Request

## Request

```http
POST /api/v1/bookings
```

Headers:

```http
Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000
```

Body:

```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440001",
  "ticketCategoryId": "550e8400-e29b-41d4-a716-446655440002",
  "quantity": 2,
  "voucherCode": "FLASH50"
}
```

---

# Future Improvements

Potential future enhancements:

- JWT authentication
- Payment gateway integration
- Booking expiration handling
- Redis caching
- Rate limiting
- Event-driven architecture
- Integration testing

---

# Author

Backend assessment project built using Spring Boot and PostgreSQL.