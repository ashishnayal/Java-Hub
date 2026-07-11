# Java Hub Backend

This is the backend service for **Java Hub**, built with Spring Boot 3.4 and Java 21. It provides REST APIs to serve Java versions, frameworks, and topics content to the frontend. It also includes an embedded, live Java Compiler service that securely compiles and executes Java code on the fly.

## Features
- **Spring Boot 3.4.1 (Java 21):** Fast, modern, and robust API development.
- **Data Seeder:** Automatically seeds the MongoDB database on startup with comprehensive JSON data from `src/main/resources/data`.
- **Live Java Compiler:** A `CompilerService` that exposes an endpoint (`/api/compile`) to accept arbitrary Java code, compile it using `javac`, run it in a 5-second sandbox timeout, and return the standard output/error.
- **Dockerized:** Fully containerized for easy deployment and local testing.

## Prerequisites
- Java 21
- Maven
- MongoDB (running locally on port 27017, or configured via URI)

## Getting Started

### Local Development (Without Docker)
1. Ensure MongoDB is running locally on port `27017` (e.g., via `docker run -p 27017:27017 mongo`).
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080`. During startup, the `DataSeeder` will automatically populate MongoDB.

### Running with Docker Compose
The easiest way to run the entire stack (Frontend + Backend + MongoDB) is from the root directory of the project using Docker Compose:
```bash
docker compose up --build -d
```

## API Endpoints
- `GET /api/versions` - List all Java versions
- `GET /api/topics` - List all Java learning topics
- `GET /api/frameworks` - List popular Java frameworks
- `GET /api/topics/{slug}` - Get specific topic details (e.g., `design-patterns`)
- `POST /api/compile` - Compile and run Java code. Body: `{ "code": "..." }`

## Data Seeding
The backend contains a `DataSeeder` component that automatically scans `src/main/resources/data/topics/*.json` and populates the `topics` MongoDB collection when the server starts up. If you add or modify a `.json` file, simply restart the backend to see the changes reflected.
