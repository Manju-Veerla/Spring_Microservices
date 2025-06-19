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
│                 │     └──────────┬──────────┘     └──────────────────┘
         │                        │
         │                        │
         ▼                        ▼
┌─────────────────┐     ┌─────────────────────┐     ┌─────────────────────┐
│                 │     │                     │     │                     │
│  Auth Service   │◄───►│  User Service       │     │  Notification      │
│  (Port 9003)    │     │  (For validation)   │◄────┤  Service           │
│                 │     │                     │     │  (Port 9004)       │
└─────────────────┘     └─────────────────────┘     └─────────────────────┘
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
   
4. **User Service** - Manages user data
   - Port: 9002
   
5. **Department Service** - Manages department data
   - Port: 9001
   
6. **Notification Service** - Handles sending notifications to users
   - Port: 9004
   
## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Manju-Veerla/Spring_Microservices.git
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
docker build -t eurekaservice:latest ./EurekaService
docker build -t userservice:latest ./UserService
docker build -t deptservice:latest ./DepartmentService
docker build -t apigatewayservice:latest ./APIGatewayService
docker build -t authservice:latest ./AuthenticationService
docker build -t notificationservice:latest ./NotificationService
```


### 5. Start Services

#### Option 1: Using the Start Script (Recommended)

The easiest way to start all services is using the provided `start-services.sh` script:

```bash
# Make the script executable if it's not already
chmod +x start-services.sh

# Run the script
./start-services.sh
```

This script will:
1. Set the Kubernetes context to Docker Desktop
2. Start all required services in the correct order
3. Wait for each service to be ready before proceeding
4. Set up port forwarding for Eureka Dashboard (8761) and API Gateway (8080)

(or you can run the script manually)

```bash
# Apply Kubernetes configurations
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/eureka-service.yaml
kubectl apply -f k8s/department-service.yaml
kubectl apply -f k8s/user-service.yaml
kubectl apply -f k8s/apigateway-service.yaml
kubectl apply -f k8s/auth-service.yaml
kubectl apply -f k8s/notification-service.yaml
```

#### Option 2: Start All Services with Docker Compose

```bash
docker-compose up -d
```

#### Option 3: Start Services Individually

1. **Start Eureka Service Discovery**
   ```bash
   docker run -d -p 8761:8761 --name eureka-server eurekaservice:latest
   ```

2. **Start User Service**
   ```bash
   docker run -d -p 9002:9002 --name user-service --network=host -e SPRING_PROFILES_ACTIVE=prod userservice:latest
   ```

3. **Start Department Service**
   ```bash
   docker run -d -p 9001:9001 --name department-service --network=host -e SPRING_PROFILES_ACTIVE=prod deptservice:latest
   ```

4. **Start Authentication Service**
   ```bash
   docker run -d -p 9003:9003 --name auth-service --network=host -e SPRING_PROFILES_ACTIVE=prod authservice:latest
   ```

5. **Start Notification Service**
   ```bash
   docker run -d -p 9004:9004 --name notification-service --network=host -e SPRING_PROFILES_ACTIVE=prod notificationservice:latest
   ```

6. **Start API Gateway**
   ```bash
   docker run -d -p 8080:8080 --name api-gateway --network=host -e SPRING_PROFILES_ACTIVE=prod apigatewayservice:latest
   ```

### 6. Access the Application

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

## Testing the Application

### Using Postman

A Postman collection is provided in the `.postman` directory to help you test the API endpoints. Here's how to use it:

1. **Import the Collection**
   - Open Postman
   - Click on "Import" and select the file: `.postman/Microservice.postman_collection.json`

2. **Available Endpoints**

#### Authentication
- **Login**
  - `POST /api/auth/login`
  - Request Body:
    ```json
    {
      "username": "Test_3",
      "password": "test_3"
    }
    ```
  - Returns JWT token for authenticated requests

#### Department Service
- **Get Department by ID**
  - `GET /api/department/{id}`
  - Example: `GET /api/department/1`

- **Get All Departments**
  - `GET /api/department/all`

- **Create Department**
  - `POST /api/department/saveDept`
  - Request Body:
    ```json
    {
      "departmentName": "POSTAL",
      "address": [
        {
          "type": "PERSONAL",
          "street": "Caeclein strasse",
          "city": "Berlin",
          "pincode": "16517"
        }
      ],
      "departmentCode": "POSTAL"
    }
    ```

#### User Service
- **Create User**
  - `POST /api/users`
  - Request Body:
    ```json
    {
      "username": "Test_3",
      "password": "test_3",
      "firstName": "Test",
      "lastName": "User",
      "email": "Mailing_J@gmail.com",
      "departmentId": "1"
    }
    ```

- **Get User by ID**
  - `GET /api/users/{id}`
  - Example: `GET /api/users/1`

- **Get All Users**
  - `GET /api/users/all`

- **Get Users by Department**
  - `GET /api/users/department/{departmentId}`
  - Example: `GET /api/users/department/1`

3. **Service URLs**
   - **Eureka Dashboard**: http://localhost:8761
   - **API Gateway**: http://localhost:8080
   - **User Service**: http://localhost:9002
   - **Department Service**: http://localhost:9001
   - **Authentication Service**: http://localhost:9003
   - **Notification Service**: http://localhost:9004