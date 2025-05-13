# Use a specific version of Maven with the correct JDK version
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory to /app in the container
WORKDIR /app

# Copy the Maven pom.xml file
COPY spring-boot-rest-API/pom.xml .

# Copy the source code from the src folder (adjust the path as needed)
COPY spring-boot-rest-API/src ./src

# Run Maven to build the application, skipping tests for faster builds
RUN mvn clean package -DskipTests

# Use a JDK image to run the application
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build image
COPY --from=build /app/target/spring-boot-rest-API-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the JAR file using Java
ENTRYPOINT ["java", "-jar", "app.jar"]
