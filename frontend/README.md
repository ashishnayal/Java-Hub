# Java Hub Frontend

This is the frontend interface for **Java Hub**, built with [Next.js](https://nextjs.org), React, and Tailwind CSS. It provides a beautiful, modern, and interactive platform for learning Java topics, frameworks, and practicing interview questions.

## Features
- **Next.js App Router:** Server-Side Rendering (SSR) for blazing fast performance and excellent SEO.
- **Dynamic Content:** Fetches topic modules, frameworks, and Java release history dynamically from the Spring Boot backend.
- **Live Interactive Code Blocks:** Integrates a custom `CodeBlock` component powered by `react-syntax-highlighter` that allows users to seamlessly edit Java code snippets and execute them directly against the backend compiler API.
- **Beautiful UI:** A dark-mode optimized, glassmorphism-inspired design system with smooth framer-motion animations.

## Prerequisites
- Node.js (v18 or higher)
- npm or yarn
- The Java Hub Spring Boot backend running locally or deployed.

## Getting Started

### Local Development
1. Install dependencies:
   ```bash
   npm install
   ```
2. Configure the API endpoint:
   Create a `.env.local` file in the root of the frontend folder (or set the environment variable) and ensure it points to the backend:
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080
   ```
3. Run the development server:
   ```bash
   npm run dev
   ```
4. Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

### Running with Docker Compose
The easiest way to run the entire stack (Frontend + Backend + MongoDB) is from the root directory of the project using Docker Compose:
```bash
docker compose up --build -d
```
This maps the frontend service automatically to port 3000.

## Interactive Java Compiler
Any markdown code block parsed from the backend that specifies the language as `java` will automatically be rendered as an interactive IDE block. The user can type, modify the code, and hit "Run". The frontend POSTs this directly to the backend's `/api/compile` endpoint, which securely compiles and executes the Java code, streaming back the output.
