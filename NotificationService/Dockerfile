# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /NotificationService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /NotificationService
COPY --from=build /NotificationService/target/*.jar NotificationService.jar
ENTRYPOINT ["java", "-jar", "NotificationService.jar"]
EXPOSE 9004
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:9001/actuator/health || exit 1
