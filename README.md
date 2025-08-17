# Microservices Architecture Project

A comprehensive microservices-based application demonstrating various architectural patterns and best practices in distributed systems.

## 🚀 Project Overview

This project showcases a microservices architecture with multiple services working together to provide a complete banking application. The system includes services for accounts, cards, loans, service discovery, API gateway, and configuration management.

## 🏗️ Architecture

```
microservice/
├── section6/        # Initial microservices implementation
├── section7/        # Enhanced configuration and service discovery
├── section8/        # Eureka service discovery
├── section9/        # API Gateway implementation
├── section10/       # Advanced routing and filtering
├── section11/       # Distributed tracing and monitoring
└── section12/       # Docker and containerization
```

## 🛠️ Core Services

### 1. Account Service
- Manages customer accounts
- Handles account creation, updates, and queries
- Integrates with other services for complete banking operations

### 2. Card Service
- Manages credit/debit cards
- Handles card issuance and management
- Implements card-related business logic

### 3. Loans Service
- Manages loan applications and processing
- Handles loan approvals and EMI calculations
- Integrates with account service for fund disbursement

### 4. Service Discovery (Eureka)
- Service registration and discovery
- Load balancing and failover support
- Service health monitoring

### 5. API Gateway (Spring Cloud Gateway)
- Single entry point for all client requests
- Request routing and load balancing
- Authentication and authorization
- Rate limiting and circuit breaking

### 6. Config Server
- Centralized configuration management
- Environment-specific configurations
- Dynamic configuration updates

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Docker (for containerized deployment)
- Git

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/nvm-10/microservice.git
   cd microservice
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Start the services**
   - Start the Eureka Server first
   - Then start the Config Server
   - Finally, start the microservices in any order

## 🐳 Docker Deployment

To run the entire application using Docker Compose:

```bash
docker-compose up --build
```

## 🌐 API Documentation

API documentation is available through Swagger UI when services are running:
- Accounts Service: `http://localhost:8080/swagger-ui.html`
- Cards Service: `http://localhost:9000/swagger-ui.html`
- Loans Service: `http://localhost:8090/swagger-ui.html`

## 🔍 Monitoring

The application includes monitoring capabilities:
- Spring Boot Actuator endpoints
- Distributed tracing with Spring Cloud Sleuth and Zipkin
- Hystrix Dashboard for circuit breaker monitoring

## 🧪 Testing

Run tests for all services:
```bash
mvn test
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📫 Contact

For any queries or support, please contact the repository owner.

---

<div align="center">
  <p>Built with ❤️ using Spring Boot and Spring Cloud</p>
  <p>Last updated: August 2023</p>
</div>
