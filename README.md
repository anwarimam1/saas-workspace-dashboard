# AI-Powered SaaS Workspace Management Dashboard

A full-stack SaaS application for managing users and workspaces with role-based access control, JWT authentication, AI-powered analytics, and Dockerized deployment.

## Features

### Authentication & Authorization

* JWT-based authentication
* Secure login system
* Role-based access control (ADMIN / USER)
* Protected routes and APIs

### User Management

* Create and manage users
* Assign roles
* View user details
* Admin-only operations

### Workspace Management

* Create workspaces
* Update workspace details
* Delete workspaces
* Track workspace information

### AI-Powered Insights

* AI-generated workspace analytics
* Administrative insights dashboard
* AI service abstraction supporting multiple providers
* Prompt engineering and analytics reporting

### Dashboard

* Admin dashboard
* User dashboard
* Workspace statistics
* Responsive UI

### Dockerized Architecture

* Frontend containerized with Docker
* Backend containerized with Docker
* PostgreSQL containerized with Docker
* Inter-container networking

---

## Tech Stack

### Frontend

* React
* Vite
* React Router
* Axios
* Recharts
* Tailwind CSS

### Backend

* Java 24
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT Authentication
* Maven

### Database

* PostgreSQL

### AI Integration

* Gemini API
* Ollama Integration

### DevOps & Deployment

* Docker
* Docker Networking
* Git & GitHub

---

## System Architecture

```text
React Frontend
       |
       v
Spring Boot REST API
       |
       v
Spring Security + JWT
       |
       v
PostgreSQL Database

       |
       +------> AI Analytics Layer
                    |
                    +--> Gemini
                    +--> Ollama
```

---

## Project Structure

```text
saas-workspace-dashboard
│
├── frontend
│   ├── src
│   ├── public
│   ├── Dockerfile
│   └── package.json
│
├── backend
│   ├── src
│   ├── Dockerfile
│   ├── pom.xml
│   └── application.properties
│
└── README.md
```

---

## Running Locally

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## Docker Setup

### Build Backend Image

```bash
docker build -t workspace-dashboard-backend ./backend
```

### Build Frontend Image

```bash
docker build -t workspace-dashboard-frontend ./frontend
```

### Run PostgreSQL

```bash
docker run --name workspace-postgres \
-e POSTGRES_DB=dashboard_db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=password \
-p 5433:5432 \
-d postgres:16
```

### Run Backend

```bash
docker run --name workspace-backend \
--network workspace-network \
-e DB_URL=jdbc:postgresql://workspace-postgres:5432/dashboard_db \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=password \
-p 8080:8080 \
-d workspace-dashboard-backend
```

### Run Frontend

```bash
docker run --name workspace-frontend \
-p 3000:80 \
-d workspace-dashboard-frontend
```

---

## Security

* JWT Authentication
* Role-based authorization
* Protected REST endpoints
* Password hashing with BCrypt
* Secure API communication

---

## Key Learning Outcomes

* Full-stack application development
* Spring Security and JWT authentication
* REST API design
* PostgreSQL database management
* Docker containerization
* Database migration and backup/restore
* AI integration in enterprise applications
* Role-based access control implementation

---

## Future Improvements

* Docker Compose orchestration
* CI/CD pipeline with GitHub Actions
* Cloud deployment (AWS / Azure)
* Refresh tokens
* User registration workflow
* Monitoring and observability
* Automated testing coverage

---

## Author

**Anwar Imam**

GitHub: https://github.com/anwarimam1
