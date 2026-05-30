# Spring AI project instructions

Use these instructions when suggesting or generating code for this repository.

## Project context

- This is a Gradle-based Java application named `learn-spring-ai`.
- Use Java 21 language features only when they improve clarity and remain easy to read.
- The project uses Spring Boot `4.0.5` and Spring AI `2.0.0-M4`.
- Keep source code under the existing base package: `com.github.sivaone.ai`.
- Follow the current formatting style enforced by Spotless with `googleJavaFormat()`.

## Code organization

- Put Spring Boot startup code in `com.github.sivaone.ai`.
- Put AI-related classes in focused subpackages such as `com.github.sivaone.ai.chat`, `service`, `controller`, or `config`.
- Prefer small, single-purpose classes.
- Use Spring-managed components (`@Service`, `@Component`, `@Configuration`, `@RestController`) instead of static utility classes when behavior depends on framework services.
- Prefer constructor injection over field injection.
- Keep controllers thin and move business logic into services.

## Spring and Spring AI guidance

- Prefer Spring AI abstractions over hand-written HTTP calls to OpenAI.
- Favor using Spring AI beans such as chat models or clients that are provided by the configured starter.
- When adding AI integration, design code so prompts, model interaction, and response mapping are easy to test.
- Do not hardcode API keys, model secrets, or endpoints in Java code.
- Read configuration from Spring properties.
- If new configuration is required, add sensible defaults only when safe, and document the property in code comments or surrounding docs.

## Configuration conventions

- Main application properties live in `src/main/resources/application.properties`.
- Test-only overrides live in `src/test/resources/application-test.properties`.
- Preserve the use of the `test` Spring profile in tests.
- Never replace test-only placeholders with real credentials.
- When suggesting environment-based configuration, prefer standard Spring property placeholders and environment variables.

## Testing expectations

- Use JUnit 5 and Spring Boot test support.
- Keep tests under `src/test/java` in matching packages.
- Prefer focused unit tests where possible; use `@SpringBootTest` only when application context coverage is needed.
- Avoid tests that call real external AI services.
- Mock or stub model interactions when practical.
- Keep tests compatible with the existing `@ActiveProfiles("test")` convention.

## Build and quality expectations

- Assume Gradle is the build tool.
- Keep generated code compatible with `./gradlew test`.
- Respect JaCoCo-backed test execution and avoid introducing code that is difficult to cover.
- Keep imports clean and avoid unused dependencies.
- Do not introduce Lombok, new frameworks, or extra libraries unless they provide clear value and fit the current stack.

## Style preferences

- Write idiomatic, production-ready Java.
- Prefer descriptive method and class names over abbreviations.
- Add concise JavaDoc only when the intent is not obvious from the code.
- Handle nullability and error cases explicitly.
- Avoid placeholder implementations unless the user explicitly asks for a scaffold; otherwise provide a complete, runnable implementation.

## When modifying existing code

- Preserve existing package names and public APIs unless a change is clearly required.
- Extend the current structure instead of reorganizing unrelated files.
- If completing `OpenAiChatCompletion`, turn it into a proper Spring component/service that uses Spring AI rather than leaving a raw TODO.
- Keep examples and sample endpoints minimal and easy to run locally.

## What to avoid

- Do not hardcode secrets.
- Do not add direct network calls in tests.
- Do not introduce unrelated refactors.
- Do not generate code that conflicts with Google Java Format.

