# Event Ticket Booking System

A backend system for managing event ticket bookings with support for:

## Users:
- Browse concerts
- View ticket categories and prices
- Reserve tickets
- Apply promotional vouchers
- Track booking status

## Administrators:
- Monitor bookings
- Manage / publish new concert's ticket
- Validate ticket availability
- Manage voucher campaigns
- Handle failed bookings
- Update booking status manually when necessary

Built with Java Spring Boot, PostgreSQL and Docker.

---

# Features

## Concert Management
- Create concerts
- Publish concerts
- View available concerts

## Ticket Category Management
- Create ticket categories
- View ticket category information by concert

## Voucher System
- Create discount vouchers
- View list vouchers

## Booking System
- Create bookings
- Prevent overselling during concurrent requests
- Apply vouchers to bookings
- Handle duplicate booking retries using idempotency keys
- Handle approve or failed booking

---

# Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- OpenAPI
- Docker

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

Database schema is provided in:

schema.sql

Sample seed data is provided in:

data.sql


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
/api/v1/swagger-ui.html
```

---

# Running the Project


## Clone project

```bash
git clone <repository-url>
```

---

## Environment Variables

Create a `.env` file in the project root:

```env
DB_URL=jdbc:postgresql://localhost:5432/ticket-booking
DB_USERNAME=postgres
DB_PASSWORD=1234
```

The application reads database configuration from environment variables.

Example application.properties:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

---

## Run PostgreSQL using Docker

```bash
docker-compose up -d
```

---

## Run application

```bash
mvn clean install 
mvn spring-boot:run
```
---

# Running with Docker

## Build application

```bash
mvn clean package
```

## Start containers

```bash
docker-compose up --build
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
  "ticketCategoryId": "550e8400-e29b-41d4-a716-446655440002",
  "quantity": 2,
  "voucherCode": "FLASH50"
}
```

---

# Project Structure

```text
├── booking 
├── concert 
├── ticket 
├── voucher 
├── common 
├── config 
└── exception
```

---

# Coding guideline & Convention

## Project Structure

The project follows feature-based modular structure:

```text
booking/
concert/
ticket/
voucher/
```

Each module contains:

- controller
- service
- repository
- dto
- entity
- mapper

---

# Naming convention

## Classes

- PascalCase

Example:
```text
BookingService
ConcertController
```

## Variables

- camelCase

Example:
```text
ticketCategory
startTime
```

## Constant

- UPPER_CASE

Example:
```text
MIN_USAGE
QUANTITY_MIN
```

---

# API Response Format

All APIs return consistent response format using

```text
ApiResponse<T>
```

Example

```json
{
  "status" : "Success",
  "message" : "Created successfully",
  "data" : {}
}
```

---

# Exception Handling

Global exception handling is implemented using:

```text
GlobalExceptionHandler
```

---

# Validation

Request validation uses:

```text
jakarta.validation
```

Example

```java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NotNull
@NotBlank
```

---

# Testing

Run tests:

```bash
mvn test
```

The project includes:

* integration tests for concurrent booking scenarios


---

# Assumptions & Limitations

## Assumptions

* A booking can only purchase one ticket category at a time.
* Booking creation is completed immediately without payment gateway integration.
* Vouchers are assumed to be created and managed internally by the operation team.
* Voucher updates and deletions are not supported in the current implementation.

---

## Limitations

* Authentication and authorization are not implemented.
* The system currently uses pessimistic locking, which may become a bottleneck under extremely high traffic.
* Queue-based processing and distributed inventory management are not implemented.
* Email/SMS notification handling is not supported.
* Booking expiration and automatic cancellation are not implemented.


---

# Future Improvements

Potential future enhancements:

- JWT authentication
- Payment gateway integration
- Booking expiration handling
- Redis caching
- Rate limiting
- Advanced integration testing
- Queue-based booking architecture

---

# Author

Backend assessment project built using Spring Boot and PostgreSQL.