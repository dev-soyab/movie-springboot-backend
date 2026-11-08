# Movie Spring Boot Backend

REST API for managing movies, theaters, showtimes, users, and bookings. The application is built with Spring Boot, Java, Maven, Spring Data JPA, and PostgreSQL.

## Requirements

- Java 17 or later
- PostgreSQL
- The Maven Wrapper included in this repository

## Configure PostgreSQL

Create a PostgreSQL database named `moviedb`. The default connection URL is `jdbc:postgresql://localhost:5432/moviedb`, and the default username is `postgres`.

Set the connection values through environment variables when they differ from the defaults. For example, in PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/moviedb"
$env:SPRING_DATASOURCE_USERNAME = "postgres"
$env:SPRING_DATASOURCE_PASSWORD = "<your-password>"
```

Do not commit real database passwords to this repository. For deployments, provide credentials through the platform's secret-management mechanism.

Hibernate is currently configured with `spring.jpa.hibernate.ddl-auto=update`, which updates the schema automatically. This is convenient for development; use a versioned database migration tool such as Flyway or Liquibase before production.

## Run locally

From the project root on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Or build and run the executable JAR:

```powershell
.\mvnw.cmd clean package
java -jar target\movie-springboot-backend-0.0.1-SNAPSHOT.jar
```

The API is served on port `8080` by default.

## Build and test

```powershell
.\mvnw.cmd clean verify
```

## API overview

| Resource | Base path |
| --- | --- |
| Movies | `/api/movies` |
| Theaters | `/api/theaters` |
| Showtimes | `/api/showtimes` |
| Bookings | `/api/bookings` |
| Authentication and users | `/api/auth` |

Most resources provide CRUD endpoints. For available operations and request/response fields, see the controllers under `src/main/java/com/project/movie/controller`.

## Deployment

For cluster deployment, package the application as a container image and provide database connection settings through environment variables or secrets. The database hostname must be reachable from the application container; `localhost` refers to the container itself. This repository does not currently include a Dockerfile or Kubernetes manifests.
