# Microservices Architecture with Spring Cloud and Kubernetes

This project demonstrates a microservices architecture using Spring Cloud, Eureka Service Discovery, and Kubernetes. It includes multiple microservices that communicate with each other to provide a complete application with JWT-based authentication, service discovery, and API gateway routing.

## Features

- **JWT-based Authentication**: Secure authentication using JSON Web Tokens
- **Service Discovery**: Automatic service registration and discovery with Eureka
- **API Gateway**: Single entry point for all client requests with request routing
- **Distributed Configuration**: Centralized configuration management
- **Containerized Deployment**: Docker and Kubernetes support for easy deployment
- **Load Balancing**: Client-side load balancing with Spring Cloud LoadBalancer
- **Resilience**: Circuit breakers and retry mechanisms for fault tolerance

## Architecture Overview

```
┌─────────────────┐     ┌─────────────────────┐     ┌──────────────────┐
│                 │     │                     │     │                  │
│  API Gateway    │◄───►│  User Service      │◄────┤  MySQL (User DB) │
│  (Spring Cloud  │     │  (Port 9002)       │     │                  │
│   Gateway)      │     │                     │     └──────────────────┘
│  (Port 8080)    │     └──────────┬──────────┘
│                 │                │
└────────┬────────┘                │
         │                           │
         │                           │
         ▼                           ▼
┌─────────────────┐     ┌─────────────────────┐     ┌──────────────────┐
│                 │     │                     │     │                  │
│  Eureka Server  │◄───►│  Department Service │◄────┤  MySQL (Dept DB)  │
│  (Port 8761)    │     │  (Port 9001)       │     │                  │
│                 │     └─────────────────────┘     └──────────────────┘
         │
         │
         ▼
┌─────────────────┐     ┌─────────────────────┐
│                 │     │                     │
│  Auth Service   │◄───►│  User Service       │
│  (Port 9003)    │     │  (For validation)   │
│                 │     │                     │
└─────────────────┘     └─────────────────────┘
```

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Docker Desktop with Kubernetes enabled
- kubectl command-line tool
- Postman or any API testing tool
- MySQL 8.0 or higher (for local development)
- Minikube (for local Kubernetes cluster) or access to a Kubernetes cluster

## Services

1. **Eureka Server** - Service discovery server
   - Port: 8761
   - URL: http://localhost:8761

2. **API Gateway** - Single entry point for all client requests
   - Port: 8080
   - Routes requests to appropriate microservices

3. **Authentication Service** - Handles user authentication and JWT token generation
   - Port: 9003
   - Endpoints:
     - `POST /api/v1/auth/login` - Authenticate user and get JWT token
       ```json
       {
         "username": "user@example.com",
         "password": "password123"
       }
       ```
     - Returns: `{ "token": "jwt.token.here" }`

4. **User Service** - Manages user data
   - Port: 9002
   - Endpoints: 
     - `POST /users` - Create a new user
     - `GET /users` - Get all users
     - `GET /users/{id}` - Get user by ID
     - `GET /users/department/{departmentId}` - Get users by department
     - `POST /auth/validate` - Validate user credentials (internal use by Auth Service)

5. **Department Service** - Manages department data
   - Port: 9001
   - Endpoints:
     - `POST /departments` - Create a new department
     - `GET /departments` - Get all departments
     - `GET /departments/{id}` - Get department by ID

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Microservices
```

### 2. Build the Project

```bash
# Build all services
mvn clean install
```

### 3. Build Docker Images

```bash
# Build and tag Docker images for each service
docker build -t eurekaservice:local ./EurekaService
docker build -t userservice:local ./UserService
docker build -t deptservice:local ./DepartmentService
docker build -t apigatewayservice:local ./APIGatewayService
docker build -t authservice:local ./AuthenticationService
```

### 4. Deploy to Kubernetes

```bash
# Apply Kubernetes configurations
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/eureka-service.yaml
kubectl apply -f k8s/department-service.yaml
kubectl apply -f k8s/user-service.yaml
kubectl apply -f k8s/apigateway-service.yaml
kubectl apply -f k8s/auth-service.yaml
```

### 5. Access the Application

1. **Access Eureka Dashboard**:
   ```bash
   kubectl port-forward svc/eureka-server 8761:8761
   ```
   Then open: http://localhost:8761

2. **Access API Gateway**:
   ```bash
   kubectl port-forward svc/api-gateway 8080:8080
   ```
   Then access APIs through: http://localhost:8080

## Monitoring and Logging

- **Eureka Dashboard**: http://localhost:8761
- **Spring Boot Actuator**: Available at `/actuator` on each service
- **Logs**: View logs using `kubectl logs <pod-name>`

## License

This project is licensed under the MIT License - see the LICENSE file for details.
