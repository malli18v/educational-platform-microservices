# Teacher-Student Educational Platform

A simple educational platform built using Spring Boot Microservices.

## Features

- Teacher and student authentication
- Teachers can create and manage courses
- Students can view courses
- Students can comment on courses
- Teachers can view student comments
- JWT-based authentication and authorization

## Microservices

- Config Server - Centralized configuration
- Eureka Server - Service discovery
- Auth Service - User registration and authentication
- Course Service - Course management
- Comment Service - Student comments

## Technologies

- Java 8
- Spring Boot
- Spring Cloud
- Spring Security
- JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Git

## Architecture

```text
                 Config Server
                     |
                     v
                 Eureka Server
                     |
          +----------+----------+
          |          |          |
          v          v          v
      Auth       Course      Comment
     Service     Service      Service
          |          |          |
          v          v          v
      PostgreSQL PostgreSQL PostgreSQL
