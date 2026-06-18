# Task Management System API

A secure and scalable RESTful Task Management System built using Spring Boot. The application provides user authentication, authorization, task management, JWT security, OAuth2 login, refresh tokens, pagination, validation, and API documentation using Swagger.

---

## Features

### Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* Refresh Token Authentication
* Logout with Refresh Token Revocation
* Password Encryption using BCrypt
* Google OAuth2 Login
* Role-Based Access Control (RBAC)
* Method-Level Security using Spring Security

### Task Management

* Create Task
* Get All Tasks
* Get Task By ID
* Delete Task
* User Ownership Validation
* Secure Access to User Resources

### Security

* Spring Security
* JWT Access Tokens
* Refresh Tokens
* OAuth2 Authentication
* BCrypt Password Hashing
* Role-Based Authorization

### API Enhancements

* Request Validation
* Global Exception Handling
* Pagination
* Swagger/OpenAPI Documentation

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Authentication

* JWT (JSON Web Token)
* OAuth2 Google Login
* Refresh Tokens

### Tools & Libraries

* Maven
* Lombok
* ModelMapper
* Swagger/OpenAPI
* BCrypt Password Encoder

---

## System Architecture

```text
Client (Postman / Swagger / Frontend)
                |
                v
      Spring Security Filter
                |
                v
       JWT Authentication
                |
                v
           Controllers
                |
                v
            Services
                |
                v
         Spring Data JPA
                |
                v
              MySQL
```

---

## Authentication Flow

### User Login

```text
User Login
     |
     v
AuthenticationManager
     |
     v
Validate Credentials
     |
     v
Generate JWT Token
     |
     v
Generate Refresh Token
     |
     v
Store Refresh Token in Database
```

### Refresh Token Flow

```text
Access Token Expired
        |
        v
Send Refresh Token
        |
        v
Validate Refresh Token
        |
        v
Generate New JWT
```

### Logout Flow

```text
User Logout
      |
      v
Delete Refresh Token
      |
      v
Refresh Token Invalidated
```

---

## Project Structure

```text
src/main/java

├── controller
│   ├── AuthController
│   ├── TaskController
│   └── AdminController
│
├── entity
│   ├── Users
│   ├── Task
│   └── RefreshToken
│
├── repository
│   ├── UserRepository
│   ├── TaskRepository
│   └── RefreshTokenRepository
│
├── service
│   ├── UserService
│   ├── TaskService
│   └── RefreshTokenService
│
├── serviceImpl
│   ├── UserServiceImpl
│   ├── TaskServiceImpl
│   └── RefreshTokenServiceImpl
│
├── security
│   ├── JwtAuthenticationFilter
│   ├── JwtTokenProvider
│   ├── CustomUserDetailsService
│   └── OAuth2AuthenticationSuccessHandler
│
├── exception
│   ├── ApiException
│   ├── UserNotFound
│   ├── TaskNotFound
│   └── GlobalExceptionHandler
│
└── payload
    ├── UserDTO
    ├── LoginDTO
    ├── JwtAuthResponse
    ├── RefreshTokenRequest
    └── LogoutRequest
```

---

## Database Design

### Users Table

| Column   | Type    |
| -------- | ------- |
| id       | BIGINT  |
| name     | VARCHAR |
| email    | VARCHAR |
| password | VARCHAR |
| role     | VARCHAR |

---

### Tasks Table

| Column      | Type    |
| ----------- | ------- |
| id          | BIGINT  |
| title       | VARCHAR |
| description | VARCHAR |
| status      | VARCHAR |
| user_id     | BIGINT  |

---

### Refresh Token Table

| Column      | Type      |
| ----------- | --------- |
| id          | BIGINT    |
| token       | VARCHAR   |
| expiry_date | TIMESTAMP |
| user_id     | BIGINT    |

---

## API Endpoints

### Authentication APIs

#### Register User

```http
POST /api/auth/register
```

#### Login User

```http
POST /api/auth/login
```

Response:

```json
{
  "token": "jwt-token",
  "tokenType": "Bearer",
  "refreshToken": "refresh-token"
}
```

#### Refresh JWT Token

```http
POST /api/auth/refresh-token
```

Request:

```json
{
  "refreshToken": "refresh-token"
}
```

Response:

```json
{
  "token": "new-jwt-token",
  "tokenType": "Bearer",
  "refreshToken": "refresh-token"
}
```

#### Logout User

```http
POST /api/auth/logout
```

Request:

```json
{
  "refreshToken": "refresh-token"
}
```

Response:

```json
{
  "message": "Logged out successfully"
}
```

---

### Task APIs

#### Create Task

```http
POST /api/{userId}/tasks
```

#### Get All Tasks

```http
GET /api/{userId}/tasks?pageNo=0&pageSize=5
```

#### Get Task By ID

```http
GET /api/{userId}/tasks/{taskId}
```

#### Delete Task

```http
DELETE /api/{userId}/tasks/{taskId}
```

---

### Admin APIs

#### Admin Dashboard Test API

```http
GET /api/admin/hello
```

Authorization:

```text
ROLE_ADMIN
```

---

## Role-Based Access Control (RBAC)

### ROLE_USER

* Create Tasks
* View Own Tasks
* View Task Details

### ROLE_ADMIN

* All USER Permissions
* Delete Tasks
* Access Admin Endpoints

---

## Validation

Implemented using Jakarta Validation.

Examples:

* Name cannot be blank
* Email must be valid
* Password cannot be empty

Example:

```java
@NotBlank(message = "Name is required")
private String name;

@Email(message = "Invalid Email")
private String email;
```

---

## Global Exception Handling

Implemented using:

```java
@RestControllerAdvice
```

Handled Exceptions:

* UserNotFound
* TaskNotFound
* ApiException
* Validation Exceptions

Example Response:

```json
{
  "message": "Task id 5 not found"
}
```

---

## Pagination

Example:

```http
GET /api/1/tasks?pageNo=0&pageSize=5
```

Benefits:

* Faster API responses
* Better scalability
* Reduced memory usage
* Efficient database querying

---

## Swagger Documentation

After running the application:

```text
http://localhost:8080/swagger-ui/index.html
```

Features:

* Interactive API Testing
* JWT Authorization Support
* API Documentation
* Request/Response Examples

---

## Screenshots

Create a folder:

```text
screenshots/
```

Add:

```text
swagger-ui.png
login-api.png
refresh-token-api.png
pagination-api.png
admin-api.png
```

README Example:

```md
![Swagger UI](screenshots/swagger-ui.png)
```

---

## Running the Application

### Clone Repository

```bash
git clone https://github.com/yourusername/task-management-system.git
```

### Navigate to Project

```bash
cd task-management-system
```

### Configure Database

Update:

```properties
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### Run Application

```bash
mvn spring-boot:run
```

---


## Author

**Korada Kishore**

Backend Java Developer

GitHub: https://github.com/Kishorekorada78
