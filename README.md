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

### 3. Set Up Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
# Database Configuration
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=userdb
MYSQL_USER=user
MYSQL_PASSWORD=password

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRATION_MS=86400000  # 24 hours
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

### 1. User Registration
```http
POST /user-service/users
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "departmentId": 1
}
```

### 2. User Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "securePassword123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "username": "john.doe@example.com",
  "email": "john.doe@example.com",
  "roles": ["ROLE_USER"]
}
```

### 3. Making Authenticated Requests
Include the JWT token in the `Authorization` header for all protected endpoints:
```http
GET /api/users/me
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### 4. Token Refresh (if implemented)
```http
POST /api/v1/auth/refresh-token
Authorization: Bearer <refresh-token>
```

## API Documentation

### Authentication Service API

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}
```

#### Validate Token
```http
POST /api/v1/auth/validate
Authorization: Bearer <token>
```

#### Get Current User
```http
GET /api/v1/auth/me
Authorization: Bearer <token>
```

### User Service API

#### Create User
```http
POST /user-service/users
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "departmentId": 1
}
```

#### Get All Users
```http
GET /user-service/users
Authorization: Bearer <token>
```

#### Get User by ID
```http
GET /user-service/users/{id}
Authorization: Bearer <token>
```

### Department Service API

#### Create Department
```http
POST /department-service/departments
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Engineering",
  "address": "Building 1"
}
```

#### Get All Departments
```http
GET /department-service/departments
Authorization: Bearer <token>
```

## Development

### Running Locally

1. Start the required services:
   ```bash
   docker-compose up -d mysql
   ```

2. Run each service from your IDE or using Maven:
   ```bash
   # In separate terminals
   cd EurekaService && mvn spring-boot:run
   cd UserService && mvn spring-boot:run
   cd DepartmentService && mvn spring-boot:run
   cd AuthenticationService && mvn spring-boot:run
   cd APIGatewayService && mvn spring-boot:run
   ```

### Testing

Run tests for all services:
```bash
mvn test
```

### Code Style

This project uses Google Java Format. Before committing, please format your code:
```bash
mvn spotless:apply
```

## Deployment

### Building Docker Images

```bash
# Build all services
mvn clean package

# Build Docker images
docker-compose build
```

### Deploying to Kubernetes

1. Start Minikube (if using local cluster):
   ```bash
   minikube start
   ```

2. Deploy the application:
   ```bash
   kubectl apply -f k8s/mysql.yaml
   kubectl apply -f k8s/eureka-service.yaml
   kubectl apply -f k8s/department-service.yaml
   kubectl apply -f k8s/user-service.yaml
   kubectl apply -f k8s/auth-service.yaml
   kubectl apply -f k8s/apigateway-service.yaml
   ```

3. Access the application:
   ```bash
   # Get the API Gateway URL
   minikube service api-gateway --url
   ```

## Monitoring and Logging

- **Eureka Dashboard**: http://localhost:8761
- **Spring Boot Actuator**: Available at `/actuator` on each service
- **Logs**: View logs using `kubectl logs <pod-name>`

## Troubleshooting

### Common Issues

1. **Connection Refused Errors**
   - Ensure all services are running and registered with Eureka
   - Check service names and ports in application properties

2. **Database Connection Issues**
   - Verify MySQL is running and accessible
   - Check database credentials in configuration

3. **JWT Authentication Failures**
   - Ensure the same JWT secret is used across all services
   - Verify token expiration time

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Cloud
- Spring Security
- JWT (Java JWT)
- Kubernetes
- Docker

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
