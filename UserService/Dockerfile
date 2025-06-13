# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /UserService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /UserService
COPY --from=build /UserService/target/*.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]
EXPOSE 9001
