# Microservices Architecture with Spring Cloud and Kubernetes

This project demonstrates a microservices architecture using Spring Cloud, Eureka Service Discovery, and Kubernetes. It includes multiple microservices that communicate with each other to provide a complete application with JWT-based authentication.

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

### 1. Build the Project

```bash
# Build all services
mvn clean install
```

### 2. Build Docker Images

```bash
# Build and tag Docker images for each service
docker build -t eurekaservice:local ./EurekaService
docker build -t userservice:local ./UserService
docker build -t deptservice:local ./DepartmentService
docker build -t apigatewayservice:local ./APIGatewayService
docker build -t authservice:local ./AuthenticationService
```

### 3. Deploy to Kubernetes

```bash
# Apply Kubernetes configurations
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/eureka-service.yaml
kubectl apply -f k8s/department-service.yaml
kubectl apply -f k8s/user-service.yaml
kubectl apply -f k8s/apigateway-service.yaml
```

### 4. Access the Application

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

3. **Authentication Service**:
   - Base URL: http://localhost:9003
   - Login endpoint: `POST /api/v1/auth/login`
   - Include JWT token in subsequent requests: `Authorization: Bearer <token>`

## Authentication Flow

1. **Login**
   ```http
   POST /api/v1/auth/login
   Content-Type: application/json
   
   {
     "username": "user@example.com",
     "password": "password123"
   }
   ```
   - On success, returns a JWT token
   - Use this token in the `Authorization` header for protected endpoints

2. **Protected Requests**
   ```http
   GET /api/protected-endpoint
   Authorization: Bearer <your-jwt-token>
   ```

## API Documentation

### Authentication Service

- **Login**
  ```
  POST /api/v1/auth/login
  Content-Type: application/json
  
  {
    "username": "user@example.com",
    "password": "password123"
  }
  ```
  - Returns: `{ "token": "jwt.token.here" }`

### User Service

- **Create User**
  ```
  POST /user-service/users
  Content-Type: application/json
  
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "departmentId": 1
  }
  ```

- **Get All Users**
  ```
  GET /user-service/users
  ```

### Department Service

- **Create Department**
  ```
  POST /department-service/departments
  Content-Type: application/json
  
  {
    "name": "Engineering",
    "address": "Building 1"
  }
  ```

- **Get All Departments**
  ```
  GET /department-service/departments
  ```

## Monitoring

Check the status of your Kubernetes resources:

```bash
# View all resources
kubectl get all

# View pod logs
kubectl logs <pod-name>

# View service details
kubectl describe svc <service-name>
```

## Clean Up

To remove all deployed resources:

```bash
kubectl delete -f k8s/
```

## Future Improvements

1. Add authentication and authorization with Spring Security
2. Implement distributed tracing with Spring Cloud Sleuth and Zipkin
3. Add metrics collection with Prometheus and Grafana
4. Set up CI/CD pipeline
5. Implement circuit breakers with Resilience4j
6. Add API documentation with Swagger/OpenAPI

## License

This project is licensed under the MIT License - see the LICENSE file for details.
