# Educational Platform – Spring Boot Microservices

A microservices-based educational platform built using **Java, Spring Boot, Spring Security, JWT, PostgreSQL, Eureka, and Spring Cloud Config**.

The platform supports two main user roles:

* **Teacher** – create, update, and delete courses
* **Student** – view courses and post comments

## Architecture

```text
                         ┌──────────────────┐
                         │   Config Server  │
                         │      :8888       │
                         └────────┬─────────┘
                                  │
                         ┌────────▼─────────┐
                         │   Eureka Server  │
                         │      :8761       │
                         └────────┬─────────┘
                                  │
          ┌───────────────────────┼────────────────────────┐
          │                       │                        │
          ▼                       ▼                        ▼
 ┌────────────────┐     ┌────────────────┐      ┌─────────────────┐
 │  Auth Service  │     │ Course Service │      │ Comment Service │
 │     :8081      │     │     :8082      │      │      :8083      │
 └───────┬────────┘     └───────┬────────┘      └────────┬────────┘
         │                       │                        │
         ▼                       ▼                        ▼
 ┌───────────────┐       ┌───────────────┐        ┌───────────────┐
 │  PostgreSQL   │       │  PostgreSQL   │        │  PostgreSQL   │
 │   Auth DB     │       │  Course DB     │        │  Comment DB   │
 └───────────────┘       └───────────────┘        └───────────────┘
```

## Services

### 1. Config Server

Centralized configuration management for the microservices.

**Port:** `8888`

### 2. Eureka Server

Provides service discovery and registration between microservices.

**Port:** `8761`

### 3. Auth Service

Responsible for:

* User registration
* User login
* Password authentication
* JWT generation
* Role-based authentication

Supported roles:

```text
TEACHER
STUDENT
```

**Port:** `8081`

### 4. Course Service

Responsible for course management.

#### Teacher

* Create course
* Update course
* Delete course

#### Student

* View all courses
* View course details

**Port:** `8082`

### 5. Comment Service

Responsible for course comments.

#### Student

* Create comment
* View course comments
* View own comments
* Delete own comment

#### Teacher

* View comments for a course

**Port:** `8083`

---

# Technology Stack

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| Java 25             | Programming language           |
| Spring Boot         | Backend framework              |
| Spring Security     | Authentication & authorization |
| JWT                 | Stateless authentication       |
| Spring Data JPA     | Database access                |
| Hibernate           | ORM                            |
| PostgreSQL          | Relational database            |
| Spring Cloud Eureka | Service discovery              |
| Spring Cloud Config | Centralized configuration      |
| Maven               | Build tool                     |
| Git                 | Version control                |
| GitHub              | Source code repository         |

---

# Project Structure

```text
educational-platform-microservices/
│
├── config-server/
│
├── eureka-server/
│
├── authservice/
│
├── courseservice/
│
├── commentservice/
│
└── README.md
```

---

# Authentication Flow

```text
User
  |
  | Register
  ▼
Auth Service
  |
  | Login
  ▼
JWT Token
  |
  ▼
Client
  |
  | Authorization: Bearer <JWT>
  ▼
Course / Comment Service
  |
  | Validate JWT
  ▼
Check User Role
  |
  ├── TEACHER
  │
  └── STUDENT
```

The JWT is used to identify the authenticated user and determine whether the user has the required role for an API.

---

# Course APIs

## Create Course

**Teacher only**

```http
POST /courses
Authorization: Bearer <JWT>
Content-Type: application/json
```

Request:

```json
{
  "title": "Java Spring Boot",
  "description": "Learn Spring Boot and Microservices"
}
```

---

## Get All Courses

**Teacher / Student**

```http
GET /courses
Authorization: Bearer <JWT>
```

---

## Get Course

**Teacher / Student**

```http
GET /courses/{id}
Authorization: Bearer <JWT>
```

---

## Update Course

**Teacher only**

```http
PUT /courses/{id}
Authorization: Bearer <JWT>
Content-Type: application/json
```

Request:

```json
{
  "title": "Advanced Spring Boot",
  "description": "Spring Boot Microservices"
}
```

---

## Delete Course

**Teacher only**

```http
DELETE /courses/{id}
Authorization: Bearer <JWT>
```

---

# Comment APIs

## Create Comment

**Student only**

```http
POST /comments/course/{courseId}
Authorization: Bearer <JWT>
Content-Type: text/plain
```

Request:

```text
This course is very useful.
```

---

## Get Course Comments

**Teacher / Student**

```http
GET /comments/course/{courseId}
Authorization: Bearer <JWT>
```

---

## Get My Comments

**Student only**

```http
GET /comments/my
Authorization: Bearer <JWT>
```

---

## Delete Comment

**Student only**

```http
DELETE /comments/{id}
Authorization: Bearer <JWT>
```

---

# Database Configuration

Each service can use its own PostgreSQL database.

Example:

```text
education_auth
education_courses
education_comments
```

Example PostgreSQL configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/education_comments
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Running the Project

Start the services in the following order:

### 1. Config Server

```bash
cd config-server
mvn spring-boot:run
```

### 2. Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
```

### 3. Auth Service

```bash
cd authservice
mvn spring-boot:run
```

### 4. Course Service

```bash
cd courseservice
mvn spring-boot:run
```

### 5. Comment Service

```bash
cd commentservice
mvn spring-boot:run
```

Check Eureka:

```text
http://localhost:8761
```

The following services should be registered:

```text
AUTH-SERVICE
COURSE-SERVICE
COMMENT-SERVICE
```

---

# Example User Flow

## Teacher

```text
Register
   ↓
Login
   ↓
Receive JWT
   ↓
Create Course
   ↓
Update Course
   ↓
View Course Comments
```

## Student

```text
Register
   ↓
Login
   ↓
Receive JWT
   ↓
View Courses
   ↓
Open Course
   ↓
Post Comment
   ↓
View Comments
```

---

# Security

The application uses **Spring Security and JWT-based authentication**.

Role-based access is implemented using method-level authorization.

Example:

```java
@PreAuthorize("hasRole('TEACHER')")
```

Teacher APIs are protected from student access.

Student APIs can use:

```java
@PreAuthorize("hasRole('STUDENT')")
```

Multiple roles can be allowed using:

```java
@PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
```

---

# Future Improvements

The following features can be added as the project evolves:

* API Gateway
* Centralized exception handling
* DTO-based request/response models
* OpenAPI / Swagger documentation
* Feign Client communication
* Pagination and sorting
* Course enrollment
* Student progress tracking
* Teacher dashboard
* Unit and integration testing
* Docker Compose
* CI/CD using GitHub Actions
* AWS deployment
* Kafka-based asynchronous events

---

# Author

**Mallikharjunarao V**

Java Backend Developer | Spring Boot | Microservices | REST APIs

---

