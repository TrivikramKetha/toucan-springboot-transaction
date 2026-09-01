# Customer Transactions API

## 1. Project Understanding

This project implements a REST API for managing customer transactions using Java and Spring Boot.

The application supports the following operations:

- Create a new transaction
- Retrieve a transaction using Transaction ID
- Update the transaction status
- Retrieve all transactions for a Customer ID

The application uses Spring Data JPA with an H2 in-memory database for storing transactions.

---

## 2. Assumptions

The following assumptions were made while implementing the solution:

- Every transaction must have a unique Transaction ID.
- A newly created transaction starts with `PENDING` status.
- The transaction status is controlled by the system and is not required from the client during creation.
- A transaction in `PENDING` status can be changed to `COMPLETED` or `FAILED`.
- `COMPLETED` and `FAILED` are considered final statuses and cannot be changed.
- Transaction amount must be greater than or equal to `0.01`.
- Currency and transaction type are required fields.

---

## 3. Validation Rules

The following validation rules are implemented:

| Field | Validation |
|---|---|
| Transaction ID | Must not be blank and must be unique |
| Customer ID | Must not be blank |
| Amount | Must not be null and must be at least 0.01 |
| Currency | Must not be blank |
| Transaction Type | Must not be blank |
| Transaction Status | Assigned by the system as `PENDING` during creation |

Invalid requests are rejected with an appropriate HTTP error response.

---

## 4. API Endpoints

### Create Transaction

**POST** `/api/transactions`

Creates a new transaction.

Example request:

```json
{
    "transactionId": "TXN001",
    "customerId": "CUS001",
    "amount": 500.00,
    "currency": "USD",
    "transactionType": "PAYMENT"
}