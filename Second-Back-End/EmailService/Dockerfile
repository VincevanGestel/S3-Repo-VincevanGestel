# Use Maven + JDK image to build the app
FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

# Copy project files
COPY . .

# Make mvnw executable (if you have it, otherwise just use mvn)
RUN mvn clean package -DskipTests

# Use Eclipse Temurin runtime for running the app
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=0 /app/target/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
