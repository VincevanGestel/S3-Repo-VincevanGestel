# Use Maven + JDK image so we have mvnw and Java together
FROM maven:3.9.4-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy everything including mvnw, pom.xml, src/
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Optionally pre-build (can skip tests here or run tests in CI)
RUN ./mvnw clean package -DskipTests

# Expose port your Spring Boot app runs on
EXPOSE 8080

# Default command: run the Spring Boot app via mvnw
CMD ["./mvnw", "spring-boot:run"]
