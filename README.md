# Task Management System API

## Overview

Task Management System API is a backend application developed using Spring Boot that enables users to manage tasks securely through RESTful APIs. The application implements authentication and authorization using Spring Security and JWT (JSON Web Token).

This project demonstrates backend development concepts such as layered architecture, DTOs, exception handling, JWT authentication, database integration, and REST API development.

## Features

* User Registration
* User Login Authentication
* JWT Token Generation
* Secure API Access using JWT
* Create Tasks
* Update Tasks
* Delete Tasks
* View All Tasks
* Custom Exception Handling
* DTO-Based Request and Response Handling
* Role-Based Security Support
* MySQL Database Integration

## Technologies Used

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* ModelMapper

## Project Structure

src/main/java

* controller
* service
* repository
* entity
* dto
* security
* exception
* configuration

## API Endpoints

### Authentication APIs

POST /api/auth/register

POST /api/auth/login

### Task APIs

GET /api/tasks

GET /api/tasks/{id}

POST /api/tasks

PUT /api/tasks/{id}

DELETE /api/tasks/{id}

## Security Implementation

* Spring Security Configuration
* JWT Token Generation
* JWT Token Validation
* Protected REST Endpoints
* Custom UserDetailsService

## Database

MySQL is used as the relational database.

Update database configuration in application.properties before running the project.

## Running the Project

1. Clone the repository

git clone <repository-url>

2. Configure MySQL database

3. Run the application

mvn spring-boot:run

4. Access APIs using Postman

## Learning Outcomes

Through this project, I gained practical experience in:

* Spring Boot Development
* REST API Design
* DTO Pattern
* Exception Handling
* Spring Security
* JWT Authentication
* Database Integration using JPA/Hibernate
* Layered Architecture
* Git and GitHub

## Author

Kishore

Backend Developer | Java | Spring Boot
