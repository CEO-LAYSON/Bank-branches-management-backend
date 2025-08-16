# Bank Branches Management System - Backend

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Setup & Installation](#setup--installation)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Authentication](#authentication)
- [Deployment](#deployment)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Overview

A robust backend system for managing bank branches, administrators, and user access control. This system provides RESTful APIs for:

- Super Admin functionality
- Company branch management
- Branch admin management
- Role-based authentication and authorization
- User management

## Features

- **Role-Based Access Control**:
    - Super Admin (Full privileges)
    - Branch Admin (Branch-specific privileges)
    - User (Basic access)

- **Branch Management**:
    - Create, read, update, and delete branches
    - Assign administrators to branches
    - View branch statistics

- **User Management**:
    - Secure JWT authentication
    - Password encryption
    - Account status management

- **API Documentation**:
    - Interactive Swagger UI
    - OpenAPI 3.0 specification

## Technologies

### Core Stack
- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL 15**

### Additional Libraries
- **MapStruct** - For DTO-Entity mapping
- **Lombok** - For reducing boilerplate code
- **SpringDoc OpenAPI** - For API documentation
- **Hibernate Validator** - For request validation

## Setup & Installation

### Prerequisites
- Java 17 JDK
- PostgreSQL 15+
- Maven 3.8+
- Git

### Installation Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/CEO-LAYSON/Bank-branches-management-backend.git
   cd Bank-branches-management-backend
   ```

2. **Database Setup**:
    - Create a PostgreSQL database:
      ```sql
      CREATE DATABASE branch_management;
      ```

3. **Configuration**:
    - Copy the sample configuration file:
      ```bash
      cp src/main/resources/application-sample.properties src/main/resources/application.properties
      ```
    - Update the database credentials in `application.properties`:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/branch_management
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

4. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Initial Setup**:
    - The system automatically creates:
        - Default roles (SUPER_ADMIN, ADMIN, USER)
        - Super admin user (username: `superadmin`, password: `superadmin123`)

## API Documentation

The API is documented using Swagger UI and OpenAPI 3.0 specification.

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

```http
GET /api/branches
Authorization: Bearer your_jwt_token
```

Example Response:
```json
{
  "id": 1,
  "name": "Main Branch",
  "code": "MB001",
  "address": "123 Main St",
  "city": "New York",
  "active": true
}
```

## Database Schema

Key Tables:
- `users` - Stores user information
- `branches` - Contains branch details
- `roles` - Defines user roles and permissions
- `user_roles` - Junction table for user-role relationships

## Authentication

The system uses JWT (JSON Web Tokens) for authentication.

### Authentication Flow:
1. **Login**:
   ```http
   POST /api/auth/login
   Content-Type: application/json

   {
     "username": "superadmin",
     "password": "superadmin123"
   }
   ```

2. **Response**:
   ```json
   {
     "token": "eyJhbGciOiJIUzUxMiJ9...",
     "username": "superadmin",
     "email": "superadmin@example.com",
     "roles": ["ROLE_SUPER_ADMIN"],
     "branchId": null,
     "branchName": null
   }
   ```

3. **Using the Token**:
   ```http
   Authorization: Bearer your_jwt_token
   ```

## Deployment

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t bank-branches-backend .
   ```

2. Run the container:
   ```bash
   docker run -d -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/branch_management \
     -e SPRING_DATASOURCE_USERNAME=your_username \
     -e SPRING_DATASOURCE_PASSWORD=your_password \
     bank-branches-backend
   ```

## Testing

Run tests with:
```bash
mvn test
```

Test coverage report:
```bash
mvn jacoco:report
```
View report at: `target/site/jacoco/index.html`

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.