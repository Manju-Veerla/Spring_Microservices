# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /EurekaService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /EurekaService
COPY --from=build /EurekaService/target/*.jar EurekaService.jar
ENTRYPOINT ["java", "-jar", "EurekaService.jar"]
EXPOSE 8761
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8761/actuator/health || exit 1
