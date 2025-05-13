# === Stage 1: Build the application ===
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies first (uses Docker cache efficiently)
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Package the application (skip tests for faster build, remove `-DskipTests` to include them)
RUN mvn clean package -DskipTests

# === Stage 2: Run the application ===
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (adjust if needed)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
