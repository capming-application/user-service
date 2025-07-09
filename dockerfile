# ----------------------------
# Stage 1 - Build with Maven
# ----------------------------

# Use Maven with OpenJDK 21 as the build image
FROM eclipse-temurin:21-jdk-jammy as builder

# Set the working directory
WORKDIR /app

# Copy Maven build files first (to leverage Docker cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (this helps with build caching)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# -------------------------------
# Stage 2 - Run Spring Boot app
# -------------------------------

# Use a smaller JDK runtime image
FROM gcr.io/distroless/java21

# Set the working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=builder /app/target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]