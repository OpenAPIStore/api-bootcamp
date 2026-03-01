# Vehicle Registration & Dealership API

A robust Spring Boot 3.x application managing vehicle registrations, advanced vehicle features, inventory management, and buy/sell transactions. It uses Java 21, Spring Data JPA with PostgreSQL, GraphQL, OpenAPI 3.1, and OpenTelemetry for metrics/tracing.

## Architecture & Features

1. **REST & GraphQL Endpoints**: Supports both traditional REST endpoints and modern GraphQL queries/mutations. Web APIs are implemented asynchronously using `CompletableFuture`.
2. **Domain Model**:
   - `Vehicle`: Manages properties like Brand, Cost, Registration Number, FuelType, Engine Capacity, Power, Images, and a comprehensive set of >20 Vehicle Features.
   - `VehicleUser`: Tracks user information (buyer/seller/owner) with embedded billing and communication addresses.
   - `VehicleTransaction`: Handles buying/selling operations linking Users to Vehicles and tracking financials.
3. **Database**: PostgreSQL 15, managed via Spring Data JPA.
4. **Observability**: Fully integrated with Micrometer and OpenTelemetry for distributed tracing (Jaeger) and metrics (Prometheus).
5. **Security**: Secured with a dual-authentication setup supporting both **HTTP Basic Auth** and **Google OAuth2 (JWT)**.

## Prerequisites

- Docker
- Docker Compose
- Java 21 (If running locally outside of docker)

## Running the Application

This project is fully containerized using Docker Compose. The `docker-compose.yml` file spins up the PostgreSQL database, the Spring Boot API, and the entire observability stack (Jaeger, Prometheus, OpenTelemetry Collector).

To start the entire stack:

```bash
docker-compose up -d --build
```

**Database Seeding:**
When the PostgreSQL container starts for the first time, it automatically executes `database/init.sql`. This script seeds the database with:
- 2 Users (with hashed passwords and full demographic/banking data)
- 10 Cars (including Tesla, BMW, Audi, Toyota, etc.)
- 10 Bikes (including Ducati, Triumph, KTM, etc.)
- Vehicle Images mapped to their respective IDs.
- 1 historical transaction representing a mock purchase.

### Services & Ports

Once running, the following services are available:

- **Spring Boot API**: `http://localhost:8080`
- **Swagger UI (OpenAPI 3.1)**: `http://localhost:8080/swagger-ui.html`
- **GraphiQL Console**: `http://localhost:8080/graphiql`
- **PostgreSQL Database**: `localhost:5432`
- **Jaeger UI (Traces)**: `http://localhost:16686`
- **Prometheus UI (Metrics)**: `http://localhost:9090`

*Authentication:* The API endpoints are secured with a dual setup:
1. **Basic Auth**: 
   - Username: `admin`
   - Password: `admin`
2. **OAuth2 / JWT (Google)**: You can pass a valid Google OpenID Connect JWT token in the `Authorization: Bearer <token>` header. The API automatically validates it against Google's public JWK set.

## Main Endpoints

### 1. User Registration & Auth
- `POST /api/v1/auth/register`: Register a new user. Supports capturing 10+ details including banking data, demographic information (DOB, Gender, Occupation, Nationality, Government ID), shipping/communication addresses, and profile image URL. Passwords are automatically hashed using BCrypt.

### 2. Vehicle Registration & Updates
- `POST /api/v1/vehicles`: Register a new vehicle with extensive features.
- `GET /api/v1/vehicles`: Fetch all vehicles (supports pagination).
- `GET /api/v1/vehicles/{id}`: Fetch vehicle by ID.
- `PUT / PATCH /api/v1/vehicles/{id}`: Update vehicle data including the 20+ features.
- `DELETE /api/v1/vehicles/{id}`: Delete a vehicle.
- `OPTIONS /api/v1/vehicles/{id}`: View allowed HTTP methods.
- `HEAD /api/v1/vehicles/{id}`: View headers for a vehicle resource.

### 2. Inventory Management
- `GET /api/v1/inventory/total?type={CAR|BIKE}`: Returns the total count of available (unsold) vehicles.
- `GET /api/v1/inventory/by-brand?type={CAR|BIKE}`: Returns the count of available vehicles grouped by brand.

### 3. Transactions (Buy/Sell)
- `POST /api/v1/transactions`: Execute a buy or sell transaction. 
  - `BUY`: A user purchases an available vehicle. Sets the vehicle status to `SOLD` and changes ownership.
  - `SELL`: A user sells a vehicle to the platform. Sets the vehicle status to `AVAILABLE` for future inventory queries.
- `GET /api/v1/transactions/history/{email}`: Fetch the paginated transaction history for a specific user.

### 4. Media & File Uploads (Multipart/Binary)
- `POST /api/v1/media/upload`: Upload an image or file. (Expects `multipart/form-data` with a `file` part). Returns a media ID.
- `GET /api/v1/media/{id}`: Download or view the raw binary data (`application/octet-stream` or original mime type).

## Error Handling & Validation
The API features a `@RestControllerAdvice` global exception handler. 
- Validation errors (e.g., negative cost, invalid email) return structured `400 Bad Request` maps outlining exactly which fields failed.
- Not Found errors return a clean `404 Not Found` JSON structure with a timestamp.
- GraphQL similarly resolves custom exceptions into proper `GraphQLError` outputs instead of generic 500 server errors.

## CORS Configuration
CORS is globally configured to allow all origins (`*`) and all HTTP methods (`GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`, `HEAD`) for seamless integration with frontend clients (React/Angular).

## GraphQL Schemas

Available in GraphiQL (`http://localhost:8080/graphiql`). Use `admin/admin` for Authorization header if requested.
Example Query:
```graphql
query {
  getTotalAvailableVehicles
  getAllVehicles {
    id
    brand
    status
    cost
    features {
      hasSunroof
      seatingCapacity
    }
  }
}
```

## Environment Variables

Configured out of the box in `docker-compose.yml`, but you can override them:
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
- `API_USER`, `API_PASSWORD`
- `OTEL_EXPORTER_OTLP_ENDPOINT`
