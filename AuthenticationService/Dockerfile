# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /AuthenticationService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /AuthenticationService
COPY --from=build /AuthenticationService/target/*.jar AuthenticationService.jar
ENTRYPOINT ["java", "-jar", "AuthenticationService.jar"]
EXPOSE 9003
