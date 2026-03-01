# Project Architecture & Maintenance Guidelines

This application follows a **Clean Architecture** (or Domain-Driven Design inspired Layered Architecture) implemented via Spring Boot. This approach ensures separation of concerns, high maintainability, and testability.

## 1. Directory Structure

The application is modularized by technical concerns, but highly cohesive around the `Vehicle` domain.

```text
src/main/java/com/restapi/store/vehicleregistration/
├── config/        # Global configurations (Security, GraphQL Scalars)
├── controller/    # REST API Endpoints (Presentation Layer)
├── graphql/       # GraphQL Controllers & Resolvers (Presentation Layer)
├── domain/        # Entities, Enums, and Embeddables (Core Domain Layer)
├── dto/           # Data Transfer Objects (Records for input/output boundaries)
├── exception/     # Global custom exceptions
├── repository/    # Spring Data JPA Interfaces (Data Access Layer)
└── service/       # Business Logic & Transactions (Application Layer)
```

## 2. Layer Definitions

### Domain Layer (`/domain`)
- **What it is**: Contains JPA Entities (`Vehicle`, `VehicleUser`, `VehicleTransaction`, `VehicleMedia`) and Enums (`VehicleType`, `FuelType`).
- **Rule**: This layer must not depend on anything outside itself (e.g., no DTOs or Service logic). It represents the pure state and business rules of the application's core logic.

### Data Access Layer (`/repository`)
- **What it is**: Spring Data JPA repositories.
- **Rule**: Used *only* by the Service layer. Controllers should never interact directly with a repository.

### Application Service Layer (`/service`)
- **What it is**: Classes annotated with `@Service`. This is where the core business logic resides (e.g., `VehicleRegistrationService`, `VehicleTransactionService`, `InventoryService`).
- **Rule**: Services orchestrate data fetching from repositories, apply business rules (e.g., checking if a vehicle is `AVAILABLE` before a `BUY` transaction), and map Domain entities to DTOs before returning them to the controller. Methods are transactional (`@Transactional`).

### Presentation Layer (`/controller`, `/graphql`)
- **What it is**: REST Controllers and GraphQL Controllers.
- **Rule**: They strictly handle HTTP/GraphQL request routing, input validation, and asynchronous execution (`CompletableFuture`). They rely 100% on the Service layer to process data and must never contain complex business rules.

## 3. Supported Features & Integrations

- **Comprehensive HTTP Verbs**: Explicitly and automatically supported (GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD).
- **Multipart/Binary Support**: `MediaController` supports multipart uploads (`POST /api/v1/media/upload`) and binary octet-stream downloads (`GET /api/v1/media/{id}`). GraphQL supports file uploads via the custom `Upload` scalar.
- **Asynchronous Execution**: Controllers wrap service calls in `CompletableFuture.supplyAsync()` allowing Tomcat/Netty threads to free up during long database or I/O operations.
- **Pagination & Filtering**: Extensively implemented using Spring Data `Pageable` and custom `@RequestParam` (e.g., filtering inventory by `VehicleType`).
- **Global Error Handling**: Managed centrally by `GlobalExceptionHandler` (`@RestControllerAdvice`) for REST and `GraphQLExceptionResolver` for GraphQL. This guarantees consistent, sanitized error payloads.
- **Input Validation**: Uses `spring-boot-starter-validation`. DTOs are decorated with Jakarta validation constraints (`@NotBlank`, `@PositiveOrZero`, etc.) ensuring dirty data is rejected at the Presentation Layer boundaries.
- **CORS Support**: Configured globally in `CorsConfig` to allow Cross-Origin Resource Sharing.

## 4. Maintenance Guidelines

1. **Adding New Entities**:
   - Always create the Entity in `/domain`.
   - Create a corresponding Record in `/dto` for Requests and Responses.
   - Create the Repository interface.
   - Add business logic in a new or existing Service.
   - Expose the operations via REST (in `/controller`) and GraphQL (in `/graphql` and `schema.graphqls`).
2. **DTO Mapping**: Avoid using Domain entities directly in Controllers or GraphQL responses. Always map entities to their corresponding `Response` records in the Service layer to prevent lazy-loading issues and data leaks.
3. **Database Changes**: The application currently uses `spring.jpa.hibernate.ddl-auto=update`. For production, migrate this to Flyway or Liquibase for strict version control of schema changes.
4. **Testing**: Write unit tests isolating the Service layer (mocking Repositories) and WebMvc tests isolating the Controller layer (mocking Services). Contract tests (Pact) should be maintained when exposing APIs to external consumers.
