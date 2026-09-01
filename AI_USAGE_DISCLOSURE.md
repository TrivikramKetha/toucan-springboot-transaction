# AI Usage Disclosure

AI tools were used during the development of this assignment as a development assistance tool.

## AI Tools Used

- ChatGPT

## How AI Was Used

AI assistance was used for:

- Understanding the assignment requirements and starter project structure.
- Understanding Spring Boot REST API concepts and project organization.
- Supporting the implementation of the transaction APIs.
- Suggesting validation rules for transaction data.
- Supporting duplicate Transaction ID handling.
- Supporting transaction status update and status transition logic.
- Creating and reviewing JUnit test cases.
- Troubleshooting compilation and test-related issues.
- Preparing and reviewing the README documentation.

## Significant AI-Suggested Items

AI suggested implementation approaches for:

- Transaction creation and validation.
- Duplicate Transaction ID detection.
- Retrieving a transaction by Transaction ID.
- Retrieving transactions by Customer ID.
- Transaction status transitions.
- Custom exceptions for duplicate and missing transactions.
- Automated tests for successful creation, validation failure, duplicate IDs, status updates, and missing transactions.

## Changes, Corrections, and Human Review

The AI-generated suggestions were reviewed and adapted to the actual starter project and Spring Boot behavior.

One important correction was related to validation testing. Bean Validation is applied to the REST request before the service method is called, so the invalid-amount test was implemented using MockMvc against the controller rather than testing only the service method.

The transaction status validation was also adjusted so that a new transaction can be created without providing a status. The service assigns `PENDING` automatically when the transaction is created.

The status update API was implemented and verified using the query-parameter format:

`PATCH /api/transactions/{transactionId}/status?status=COMPLETED`

Final transaction states are treated as non-changeable after `COMPLETED` or `FAILED`.

## AI Limitations and Corrections

AI suggestions were not accepted blindly. The implementation was checked against the assignment requirements and the actual behavior of the starter project.

Where an AI suggestion did not match the project structure or Spring Boot validation behavior, it was modified before being included in the final implementation.

## Verification of the Final Result

The final implementation was verified in two ways:

1. REST APIs were manually tested using Postman, including:
   - Creating a transaction.
   - Getting a transaction by Transaction ID.
   - Updating transaction status.
   - Getting transactions by Customer ID.

2. Automated tests were executed using Maven.

The final clean Maven test execution completed successfully:

- Tests run: 7
- Failures: 0
- Errors: 0
- Skipped: 0
- Build: SUCCESS

The project was also pushed successfully to the private GitHub repository.

## Human Responsibility

AI was used only as a development assistance tool. The candidate reviewed, tested, modified, and verified the final implementation and remains responsible for the submitted code.