# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS build

# Set the working directory for the build
WORKDIR /app

# Copy the Maven/Gradle wrapper and related files first to cache dependencies
COPY mvnw* ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x ./mvnw

# Download dependencies (this step will be cached unless dependencies change)
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests -Pprod

RUN mv /app/target/*.jar /app/target/app.jar

# Stage 2: Create the minimal image with only the compiled application
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/app.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
