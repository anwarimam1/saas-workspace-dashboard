# SaaS Workspace Analytics Dashboard

> Production-style full-stack SaaS analytics platform built with Spring Boot, React, PostgreSQL, Docker, Nginx, GitHub Actions, and AWS EC2.

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?logo=react&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?logo=amazonaws&logoColor=white)

## Live Demo

| Resource | Link |
|----------|------|
| Live Application | http://15.207.55.33 |
| GitHub Repository | Your GitHub Repository Link |

### Demo Credentials

| Field | Value |
|-------|-------|
| Email | demo@test.com |
| Password | Demo@123 |

## Project Overview

This project simulates a production-style SaaS application where users authenticate securely, manage workspaces, and access role-based functionality through a Dockerized deployment on AWS EC2.

### Key Features

- JWT Authentication
- Role-Based Access Control (RBAC)
- PostgreSQL Integration
- Gemini AI Integration
- Dockerized Deployment
- Nginx Reverse Proxy
- GitHub Actions CI Pipeline
- AWS EC2 Deployment

## Architecture

Internet Users -> Host Nginx (Port 80) -> Spring Boot (127.0.0.1:8080) -> PostgreSQL

### Request Flow

1. Browser requests reach Nginx.
2. Nginx serves the React frontend.
3. `/api` requests are proxied internally.
4. Spring Boot processes business logic.
5. PostgreSQL stores persistent data.

## Tech Stack

| Layer | Technology |
|--------|------------|
| Frontend | React, Vite, Axios |
| Backend | Spring Boot, Spring Security |
| ORM | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Authentication | JWT |
| AI | Gemini API |
| DevOps | Docker, Docker Compose |
| Reverse Proxy | Nginx |
| CI | GitHub Actions |
| Cloud | AWS EC2 |
| OS | Amazon Linux 2023 |

## Project Structure

```text
saas-dashboard/
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── security/
│   └── config/
│
├── frontend/
│   ├── src/
│   ├── components/
│   ├── pages/
│   └── api/
│
├── docker-compose.yml
└── README.md
```

## Deployment

The application is deployed on AWS EC2 using Docker.

### Production Setup

- React served through Host Nginx
- Spring Boot accessible only through `127.0.0.1:8080`
- PostgreSQL running inside Docker
- Docker Compose orchestration
- Reverse proxy routing through Nginx

## CI Pipeline

Every push triggers GitHub Actions to:

1. Build the Spring Boot application.
2. Build the React application.
3. Create Docker images.
4. Publish images to Docker Hub.

## Key Challenges Solved

| Challenge | Solution |
|------------|----------|
| React calling localhost APIs | Switched Axios to relative `/api` routing |
| Backend publicly exposed | Bound Spring Boot to `127.0.0.1` |
| Reverse proxy configuration | Configured Host Nginx for `/` and `/api` |
| Docker networking | Used Docker Compose networking |
| Production login failure | Fixed duplicate `/api/api` routing |

## Future Improvements

- HTTPS with Let's Encrypt
- Custom Domain
- Automated Continuous Deployment
- Redis Caching
- Kubernetes Deployment