# Bloomie Platform

Bloomie is a skincare-management platform built as a modular monolith with Spring Boot. It helps users track their skin health, get AI-assisted product and routine recommendations, and book dermatological consultations, backed by subscriptions and payments handled through Stripe.

## Tech Stack

- **Java 26** / **Spring Boot 4** (Web MVC, Data JPA, Validation, Security)
- **MySQL** as the primary datastore
- **JWT** for stateless authentication
- **Stripe** for checkout, payments and webhooks
- **Google Gemini API** for AI-assisted skin analysis and support
- **springdoc-openapi** for API documentation (Swagger UI)
- **Flyway** for database migrations (present as a dependency, disabled by default — see [Configuration](#configuration))
- **Docker** / **docker-compose** for containerized builds

## Architecture

The codebase follows a **Domain-Driven Design (DDD)**-inspired modular structure. Each bounded context under `com.bloomie.platform` is organized into the same four layers:

```
<bounded-context>/
├── domain/          # Entities, value objects, aggregates, repository interfaces
├── application/      # Command/query services, internal services, ACLs
├── infrastructure/   # JPA repository implementations, external integrations (Stripe, AI, etc.)
└── interfaces/        # REST controllers, DTOs (resources), domain events
```

Cross-cutting concerns (result wrappers, i18n, base persistence config, API documentation) live in the `shared` context.

### Bounded contexts

| Context | Responsibility | Base path |
|---|---|---|
| `iam` | Identity & access management, authentication, JWT issuance | `/api/v1/authentication`, `/api/v1/users` |
| `dermatologycare` | Dermatologist profiles and availability | `/api/v1/dermatologist-profiles`, `/api/v1/availabilities` |
| `dermatologicalappointment` | Appointment booking and consultations | `/api/v1/appointments`, `/api/v1/consultations` |
| `skinanalysis` | Skin profiles, facial scans, AI-assisted skin analyses | `/api/v1/skin-profiles`, `/api/v1/facial-scans`, `/api/v1/skin-analyses` |
| `productdiscovery` | Product catalog, favorites, compatibility checks (Open Beauty Facts integration) | `/api/v1/products`, `/api/v1/favorite-products`, `/api/v1/product-compatibilities` |
| `routinemanagement` | Skincare routines and daily tracking, AI-assisted suggestions | `/api/v1/routines`, `/api/v1/daily-trackings` |
| `intelligentsupport` | AI-powered chat support | `/api/v1/support-queries`, `/api/v1/chat-messages` |
| `subscription` | Subscription plans | `/api/v1/plans`, `/api/v1/subscriptions` |
| `payments` | Stripe checkout, payments, and webhook handling | `/api/v1/payments`, `/api/v1/payments/checkout`, `/api/v1/webhook/stripe` |
| `shared` | Shared kernel: i18n, result wrappers, persistence naming strategy, OpenAPI config | — |

Contexts communicate through domain events and anti-corruption layers (`acl` packages) rather than direct cross-context dependencies.

## Getting Started

### Prerequisites

- JDK 26
- Maven (or use the bundled `./mvnw` / `mvnw.cmd` wrapper)
- MySQL 8+ running locally (or via Docker)
- A Stripe account and API keys (for payment features)
- A Google Gemini API key (for AI features)

### Running locally

1. Start a MySQL instance and create/point it at a `bloomie` database (the `dev` profile creates it automatically if missing).
2. Set the Gemini API key as an environment variable, or edit it directly in `src/main/resources/application-dev.properties`:
   ```
   GEMINI_API_KEY=your-api-key-here
   ```
3. Run the application (defaults to the `dev` profile):
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`, with interactive API docs at `http://localhost:8080/swagger-ui.html`.

### Running with Docker Compose

```bash
docker-compose up --build
```

This builds the image from the `Dockerfile` (multi-stage Maven + Temurin JDK 26 build) and runs it with the `prod` profile. You must supply the environment variables below (e.g. via a `.env` file next to `docker-compose.yml`).

## Configuration

### Environment variables (production / Docker)

| Variable | Description |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile (`prod` for the Docker image) |
| `DATABASE_URL` | MySQL host |
| `DATABASE_PORT` | MySQL port |
| `DATABASE_NAME` | MySQL database name |
| `DATABASE_USER` | MySQL username |
| `DATABASE_PASSWORD` | MySQL password |
| `PORT` | Port the application listens on (defaults to `8080`) |
| `JWT_SECRET` | Secret used to sign JWT tokens |
| `STRIPE_API_KEY` | Stripe secret API key |
| `STRIPE_WEBHOOK_SECRET` | Stripe webhook signing secret |
| `STRIPE_SUCCESS_URL` | Redirect URL on successful checkout |
| `STRIPE_CANCEL_URL` | Redirect URL on canceled checkout |
| `GEMINI_API_KEY` | Google Gemini API key for AI-assisted features |

Profiles: `application-dev.properties` (local development, verbose SQL logging, hardcoded local MySQL credentials) and `application-prod.properties` (all secrets sourced from environment variables) live under `src/main/resources/`.

### Internationalization

Response messages are localized via `spring.messages.basename=messages`, with bundles for English (`messages.properties`) and Spanish (`messages_es.properties`).

## Testing

```bash
./mvnw test
```

## Building

```bash
./mvnw clean package
```

Produces an executable JAR under `target/`.