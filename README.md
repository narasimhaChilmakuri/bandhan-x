
# Bandhan

**🌐 Bandhan: High-Scale Professional Networking Platform**
High-scale professional networking backend built with a microservices architecture.

> Tech: Spring Boot + Spring Cloud, Kafka, Redis, PostgreSQL, Neo4j, ELK, Zipkin, Docker/Kubernetes.

## Architecture

![High-Level Architecture](https://github.com/user-attachments/assets/b52ed03c-efe3-4abc-912f-731ae11911d9)

### Core components
- **API Gateway**: JWT auth + request routing
- **Service discovery/config**: Eureka + Config
- **Event streaming**: Kafka (async notifications, feed processing)
- **Datastores**
  - **PostgreSQL**: profiles / relational data
  - **Neo4j**: connection graph + recommendations
  - **Redis**: feed caching
- **Observability**: ELK (logs) + Zipkin (tracing)

## Prerequisites

Install:
- **Java 17+** (or the version required by the repo)
- **Maven 3.8+** (or Gradle, depending on the project)
- **Docker + Docker Compose**
- (Optional) **kubectl + Helm** for Kubernetes deployment

## Quick start (local)

1) **Clone**
```bash
git clone https://github.com/discreteBody/bandhan-x.git
cd bandhan-x
```

2) **Start dependencies (recommended via Docker)**
> Start Kafka, Redis, Postgres, Neo4j, Zipkin (and Elasticsearch/Kibana if included).
```bash
docker compose up -d
```

3) **Configure environment**
Create an `.env` (or configure `application.yml`) with your local URLs/credentials. Typical values:

- `SPRING_PROFILES_ACTIVE=local`
- `POSTGRES_URL=jdbc:postgresql://localhost:5432/<db>`
- `POSTGRES_USER=<user>`
- `POSTGRES_PASSWORD=<pass>`
- `NEO4J_URI=bolt://localhost:7687`
- `NEO4J_USER=<user>`
- `NEO4J_PASSWORD=<pass>`
- `REDIS_HOST=localhost`
- `REDIS_PORT=6379`
- `KAFKA_BOOTSTRAP_SERVERS=localhost:9092`
- `ZIPKIN_ENDPOINT=http://localhost:9411/api/v2/spans`

4) **Build**
```bash
mvn clean install
```

5) **Run services**
Run each microservice from its module directory, e.g.:
```bash
mvn spring-boot:run
```

## Usage

- Gateway will expose the public API (base URL depends on your gateway config).
- For protected endpoints, obtain a JWT (based on your auth service setup) and pass:
```http
Authorization: Bearer <token>
```

## Observability

- **Zipkin**: http://localhost:9411
- **Logs**: via ELK stack (URLs depend on your compose/k8s setup)

## Development notes

- Inter-service calls use **Feign**.
- Feed generation uses a **fan-out strategy** via Kafka Streams and caches results in Redis.

## Troubleshooting

- If services fail to register/discover each other, verify **Eureka** URL and service ports.
- If consumers can’t read events, verify `KAFKA_BOOTSTRAP_SERVERS` and topic creation.
- If auth fails, ensure JWT secrets/issuer configs match across gateway + auth services.
