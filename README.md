<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white" alt="Next.js" />
  <img src="https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB" />
  
  <h1>☕ Java Hub</h1>
  <p><b>The Ultimate Java Learning & Interview Preparation Platform</b></p>
</div>

<br/>

**Java Hub** is a comprehensive, full-stack application designed specifically for Java Developers. Whether you are a junior developer preparing for your first role, or a senior engineer brushing up on modern Java 26 features, this platform is your definitive guide. 

We cover everything from the birth of "Project Oak" in 1991 to the cutting-edge features of Java 26, alongside deep dives into core concepts, advanced frameworks, and real-world interview questions.

---

## 🎯 How This Helps You Crack the Interview

The technical interview process for Java developers is famously rigorous, often spanning core language fundamentals, ecosystem tools, and system design. **Java Hub is built to be your pre-interview companion.**

### 1. The "Interview Corner" 
A dedicated, flashcard-style interactive section containing **60+ high-frequency interview questions**. Instead of overwhelming you with walls of text, it groups questions by topic (e.g., Concurrency, Streams, Spring Boot) and links you directly to the underlying concepts and code examples.

### 2. Version Mastery (Java 8 to 26)
Interviews frequently focus on what changed in specific Java versions (e.g., *"What did Java 8 introduce?"* or *"Explain Records from Java 14"*). 
Our **Versions** section gives you the exact release notes, LTS status, and most importantly: **Syntax-highlighted code examples of every major feature**.

### 3. Rapid Concept Revision
Forgot how `ConcurrentHashMap` works internally? Need a quick refresher on the `Stream API` terminal operations? Our **Topics** section provides bite-sized, code-heavy explanations categorized by difficulty (Beginner, Intermediate, Advanced).

### 4. Ecosystem & Frameworks
Modern Java roles require more than just core Java. Our **Frameworks** section gives you the critical talking points, use-cases, and architecture overviews for tools like Spring Boot, Hibernate, and Apache Kafka, ensuring you sound like an experienced practitioner.

---

## 📚 Concepts Included in this Project

Java Hub doesn't just scratch the surface. It includes a massively seeded database covering:

### 📦 Java Versions (1995 – 2026)
- **Classic Java:** JDK 1.0 up to Java 7.
- **The Game Changers:** Java 8 (Lambdas, Streams, Optional).
- **The Module System:** Java 9 (Project Jigsaw).
- **Modern LTS Releases:** Java 11, 17, and 21 (Records, Virtual Threads, Pattern Matching).
- **The Cutting Edge:** Java 22 through Java 26.

### 🧠 Core & Advanced Topics
1. **Core Java & OOP:** Classes, Interfaces, Polymorphism, Encapsulation.
2. **Collections Framework:** List, Set, Map hierarchies, and internal workings (e.g., HashMap collision resolution).
3. **Stream API & Lambdas:** Functional interfaces, intermediate vs. terminal operations.
4. **Concurrency & Multithreading:** Thread lifecycles, Synchronization, `java.util.concurrent`, Virtual Threads (Project Loom).
5. **Memory Management:** Garbage Collection algorithms (G1, ZGC), JVM Memory Areas (Heap, Stack, Metaspace).
6. **Exception Handling:** Checked vs Unchecked, best practices.
7. **Design Patterns:** Singleton, Factory, Builder, Observer (implemented in modern Java).
8. **Modern Java Features:** Sealed Classes, Pattern Matching for `switch`, String Templates.

### 🛠️ Frameworks & Ecosystem
- **Spring Boot & Spring Framework:** Inversion of Control, Dependency Injection, Auto-configuration.
- **Hibernate / JPA:** ORM concepts, Entity lifecycle, Caching.
- **Apache Kafka:** Event-driven architecture, Pub/Sub.
- **JUnit & Mockito:** Test-driven development.
- **Maven & Gradle:** Build tools and dependency management.

---

## 🏗️ Technical Architecture

This project is built using a modern, scalable tech stack, proving that Java ecosystems pair beautifully with cutting-edge frontends.

| Layer      | Technology                        | Description |
| :--------- | :-------------------------------- | :---------- |
| **Frontend**   | Next.js 15, React 19, TypeScript | Server-Side Rendering (SSR), App Router, Vanilla CSS with Glassmorphism. |
| **Backend**    | Spring Boot 3.4.1, Java 21       | RESTful APIs, Service-Repository pattern, Global CORS, OpenAPI/Swagger. |
| **Database**   | MongoDB 7.0                       | Document-based NoSQL storage, seeded automatically on startup. |
| **Deployment** | Docker & Docker Compose           | Multi-stage containerized builds for seamless orchestration. |

---

## 🚀 Getting Started

### Prerequisites
- **Docker** and **Docker Compose** installed on your machine.

### 1. Run the Entire Stack via Docker
You don't need to install Java, Node, or MongoDB on your local machine. Just run:
```bash
docker compose up --build -d
```

*Note: On the very first run, the Spring Boot application will detect an empty database and automatically seed all 27 versions, 13 topics, 8 frameworks, and 60+ interview questions into MongoDB!*

### 2. Access the Application
- **Frontend UI:** Open your browser to `http://localhost:3000`
- **Backend API:** Available at `http://localhost:8080/api`
- **MongoDB Database:** Accessible locally via Compass at `mongodb://localhost:27018`

---

## 🎨 UI & UX Design
The frontend uses a custom-built, premium **Dark Mode** design system. 
- **Glassmorphism:** Frosted glass effects on navigation and cards.
- **Micro-animations:** Smooth hover states and staggered entrance animations.
- **Typography:** Professional aesthetic using `Inter`, `JetBrains Mono` for code, and `Outfit` for headings.
- **Custom Code Highlighter:** A built-in regex parser specifically designed to highlight Java syntax beautifully without heavy third-party libraries.

---
*Built by Java Developers, for Java Developers.*
