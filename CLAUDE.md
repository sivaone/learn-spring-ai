# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

A Spring Boot application for learning [Spring AI](https://spring.io/projects/spring-ai#learn). It integrates with OpenAI via Spring AI abstractions and exposes REST endpoints.

- **Spring Boot**: 4.0.6
- **Spring AI**: 2.0.0-M8 (milestone)
- **Java**: 25
- **Build**: Gradle with Spotless (Google Java Format) and JaCoCo

## Commands

```bash
# Build (also runs Spotless formatting)
./gradlew build

# Run tests (generates JaCoCo coverage report in build/reports/jacoco/)
./gradlew test

# Run a single test class
./gradlew test --tests "com.github.sivaone.ai.LearnSpringAiApplicationTests"

# Apply code formatting
./gradlew spotlessApply

# Check formatting without applying
./gradlew spotlessCheck

# Run the application
./gradlew bootRun
```

Note: `compileJava` depends on `spotlessApply`, so formatting runs automatically on every build.

## Architecture

```
com.github.sivaone.ai
├── LearnSpringAiApplication.java   # Spring Boot entry point
└── chat/
    └── OpenAiChatCompletion.java   # In-progress: chat completion via Spring AI
```

The `chat` subpackage is where AI interaction logic lives. `OpenAiChatCompletion` should become a Spring `@Service` that uses Spring AI's `ChatClient` or `ChatModel` beans (provided by `spring-ai-starter-model-openai`) rather than raw HTTP. New AI features go in focused subpackages under `com.github.sivaone.ai` — e.g., `chat`, `service`, `controller`, `config`.

## Configuration

- `src/main/resources/application.properties` — main config (currently only `spring.application.name`)
- `src/test/resources/application-test.properties` — test overrides (sets `spring.ai.openai.api-key=testkey`)
- Tests use `@ActiveProfiles("test")` to pick up test properties
- The real OpenAI API key must be supplied via environment variable or external config — never hardcoded

## Code conventions

- Constructor injection over field injection
- Thin controllers, business logic in services
- Use Spring AI abstractions (`ChatClient`, `ChatModel`) over direct HTTP calls to OpenAI
- Tests mock/stub model interactions — no calls to real external AI services
- Avoid Lombok and new frameworks unless they provide clear value
- Google Java Format is enforced by Spotless; run `./gradlew spotlessApply` before committing if the formatter hasn't run automatically