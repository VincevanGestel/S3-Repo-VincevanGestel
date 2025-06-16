# Use Maven + JDK image so we have mvnw and Java together
FROM maven:3.9.4-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy everything including mvnw, pom.xml, src/
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Pre-build (skip tests for faster builds, run tests in CI if needed)
RUN ./mvnw clean package -DskipTests

# Expose port your Spring Boot app runs on
EXPOSE 8080

# Default command: run the Spring Boot app via the packaged jar
CMD ["java", "-jar", "target/your-app.jar"]
