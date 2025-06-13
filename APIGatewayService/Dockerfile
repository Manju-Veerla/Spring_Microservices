# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /APIGatewayService
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /APIGatewayService
COPY --from=build /APIGatewayService/target/*.jar APIGatewayService.jar
ENTRYPOINT ["java", "-jar", "APIGatewayService.jar"]
EXPOSE 9191
