# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /DepartmentService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /DepartmentService
COPY --from=build /DepartmentService/target/*.jar DepartmentService.jar
ENTRYPOINT ["java", "-jar", "DepartmentService.jar"]
EXPOSE 9001
