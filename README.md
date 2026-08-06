# Incident Manager

![CI](https://github.com/kbetkowsky/incident-manager/actions/workflows/ci.yml/badge.svg)

Incident Manager is a backend project I built to practice building a complete,
realistic system on my own. I have a background in networking and IT
infrastructure, so I picked a problem I understand well: keeping track of devices
and turning their repeated problems into incidents that someone can act on.

The system watches devices. When a device reports the same problem often enough,
escalation rules turn those events into an incident. A separate notification
microservice then sends a notification over Kafka. I split the system into two
services on purpose, so they can be deployed and scaled independently. There is
also a small React frontend, so you can log in and see the data in the browser.

## Contents

- [Architecture](#architecture)
- [Tech stack](#tech-stack)
- [Getting started](#getting-started)
- [Project structure](#project-structure)
- [API](#api)
- [Screenshots](#screenshots)
- [Tests](#tests)
- [Observability](#observability)
- [Roadmap](#roadmap)
- [License](#license)

## Architecture

The project is a monorepo with two Spring Boot microservices — a core service
and a notification service — that communicate over Kafka, plus a React client.

```mermaid
flowchart LR
  UI[Frontend] --> API[incident-manager]
  API --> DB[(PostgreSQL)]
  API --> Kafka[(Kafka)]
  Kafka --> NS[notification-service]
```

The core service (`incident-manager`) uses a hexagonal (ports & adapters)
layout, so the domain does not depend on Spring or the database:

- **domain** – the model (`Device`, `Event`, `Incident`, `EscalationRule`) and
  the ports (interfaces the domain needs, like repositories).
- **application** – use cases that call the ports and run the domain logic.
- **adapters/in** – REST controllers.
- **adapters/out** – JPA persistence and Kafka publishing. These implement the
  ports.
- **config / security** – wiring and the JWT setup.

How it works: a device reports events, the escalation rules decide when repeated
events become an incident, and the new incident is published to Kafka as an
`IncidentCreated` event. The `notification-service` reads that event and sends a
notification (simulated). Messages it cannot process go to a dead-letter topic.
Requests are traced across both services with OpenTelemetry.

## Tech stack

- Java 21, Spring Boot 4
- Spring Web, Spring Data JPA, Spring Security (JWT / OAuth2 resource server)
- PostgreSQL with Flyway migrations
- Apache Kafka for messaging between services
- MapStruct for entity/domain mapping
- Micrometer Tracing + OpenTelemetry
- Actuator + Prometheus + Grafana
- JUnit 5, Mockito, Testcontainers, JaCoCo
- React + TypeScript + Vite (frontend)
- nginx serving the frontend and proxying the API
- Docker and Docker Compose (everything runs in containers)
- Gradle (Kotlin DSL), GitHub Actions for CI

## Getting started

You need Docker.

**1. Create your `.env` file:**

```bash
cp .env.example .env
```

Open it and set your own values, especially `JWT_SECRET`. It has no default, so
the app will not start without it.

**2. Start everything:**

```bash
docker compose up -d
```

This builds and runs both services and the frontend, together with Postgres,
Kafka, Prometheus and Grafana. The frontend is served by nginx, which also
proxies the API calls to the core service.

Open http://localhost:5173 and log in:

```
username: admin
password: admin123
```

This account is created only when the `dev` profile is active, which is the case
in Docker Compose.

## Project structure

```
incident-manager/       core service: REST API, domain, escalation, security
notification-service/   Kafka consumer that sends notifications
frontend/               React + Vite client, nginx config for the container
grafana/                dashboards and provisioning
docker-compose.yml      runs everything: both services, frontend, Postgres, Kafka, Prometheus, Grafana
```

## API

Some of the main endpoints. Everything except login needs a JWT:

| Method | Path | Description |
| ------ | ---- | ----------- |
| POST | `/auth/login` | Log in, returns a JWT |
| GET  | `/devices` | List devices |
| POST | `/devices` | Add a device |
| POST | `/devices/{id}/events` | Record an event for a device |
| GET  | `/incidents` | List incidents |
| POST | `/incidents/{id}/acknowledge` | Acknowledge an incident |
| POST | `/incidents/{id}/resolve` | Resolve an incident |

## Screenshots

Login (JWT authentication):

![Login](docs/screenshots/login.png)

Dashboard (incidents and devices):

![Dashboard](docs/screenshots/dashboard.png)

Grafana metrics:

![Grafana](docs/screenshots/grafana.png)

## Tests

Unit tests cover the domain and the use cases (JUnit 5 + Mockito). The controller
and security tests use Testcontainers, so they run against a real PostgreSQL
database instead of mocks.

```bash
cd incident-manager
./gradlew test
```

JaCoCo generates a coverage report at
`incident-manager/build/reports/jacoco/test/html/index.html` after the tests run.
Coverage is highest where the logic is (domain and use cases) and low in the
adapters, which are mostly generated mappers and JPA entities.

CI runs the tests on every push and pull request to `main`.

## Observability

Both services expose Actuator metrics in Prometheus format. Prometheus scrapes
them and Grafana shows them. The provisioning and a starter dashboard are in
`grafana/`. When you run it locally, Grafana is on http://localhost:3000
(admin / admin) and Prometheus on http://localhost:9090.

## Roadmap

Phase 1 (done): the core domain, escalation rules, Kafka and the notification
service, JWT security, observability, CI, and the small frontend.

Phase 2 (done): everything runs in containers and starts with one command,
secrets moved to environment variables, coverage reports with JaCoCo.

Next steps I want to add:

- Kubernetes manifests to run it locally
- Deploy to AWS (RDS, ECR, ECS Fargate)
- An AI advisor service that suggests fixes based on past incidents

## License

MIT — see [LICENSE](LICENSE).
