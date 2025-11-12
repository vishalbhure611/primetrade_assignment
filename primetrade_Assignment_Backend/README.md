# PrimeTrade Assignment Backend

A scalable REST API with Authentication & Role-Based Access Control built with Spring Boot.

## 🚀 Features

- ✅ **User Registration & Login** with password hashing (BCrypt) and JWT authentication
- ✅ **Role-Based Access Control** (USER vs ADMIN)
- ✅ **CRUD APIs** for Tasks entity
- ✅ **API Versioning** (`/api/v1/`)
- ✅ **Input Validation** using Jakarta Bean Validation
- ✅ **Global Exception Handling** with proper error responses
- ✅ **API Documentation** with Swagger/OpenAPI
- ✅ **Database Schema** using MySQL with JPA/Hibernate
- ✅ **CORS Configuration** for frontend integration
- ✅ **Secure JWT Token Handling**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🛠️ Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE primetrade_db;
```

### 2. Configuration

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/primetrade_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access Swagger UI at:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔐 API Endpoints

### Authentication (`/api/v1/auth`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/v1/auth/register` | Register a new user | No |
| POST | `/api/v1/auth/login` | Login and get JWT token | No |

**Register Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

**Login Request:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

### Tasks (`/api/v1/tasks`)

All task endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

| Method | Endpoint | Description | Auth Required | Role Required |
|--------|----------|-------------|----------------|---------------|
| GET | `/api/v1/tasks` | Get all tasks (user's own tasks, or all if admin) | Yes | USER/ADMIN |
| GET | `/api/v1/tasks/{id}` | Get task by ID | Yes | USER/ADMIN |
| POST | `/api/v1/tasks` | Create a new task | Yes | USER/ADMIN |
| PUT | `/api/v1/tasks/{id}` | Update a task | Yes | USER/ADMIN |
| DELETE | `/api/v1/tasks/{id}` | Delete a task | Yes | USER/ADMIN |

**Create Task Request:**
```json
{
  "title": "Complete assignment",
  "description": "Finish the PrimeTrade assignment",
  "status": "PENDING"
}
```

**Task Status Values:**
- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`

### User Profile (`/api/v1/users`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| GET | `/api/v1/users/profile` | Get current user profile | Yes |

## 🏗️ Project Structure

```
src/main/java/com/example/
├── config/              # Configuration classes
│   ├── AppConfig.java          # Bean configurations
│   ├── OpenApiConfig.java      # Swagger/OpenAPI config
│   └── SecurityConfig.java     # Spring Security config
├── controller/         # REST controllers
│   ├── AuthController.java    # Authentication endpoints
│   ├── TaskController.java    # Task CRUD endpoints
│   └── UserController.java    # User profile endpoints
├── dao/                 # Data Access Objects (Repositories)
│   ├── TaskRepository.java
│   └── UserRepository.java
├── dto/                 # Data Transfer Objects
│   ├── LoginDto.java
│   ├── TaskDto.java
│   └── UserDto.java
├── entity/              # JPA entities
│   ├── Role.java
│   ├── Task.java
│   ├── TaskStatus.java
│   └── User.java
├── exception/           # Exception handlers
│   └── GlobalExceptionHandler.java
├── security/            # Security components
│   ├── CustomUserDetailsService.java
│   └── JwtAuthFilter.java
├── service/             # Business logic
│   ├── TaskService.java
│   ├── TaskServiceImpl.java
│   ├── UserService.java
│   └── UserServiceImpl.java
└── util/                # Utility classes
    └── JwtUtil.java
```

## 🔒 Security Features

1. **Password Hashing**: BCrypt with strength 10
2. **JWT Authentication**: HS256 algorithm with configurable expiration
3. **Role-Based Access**: USER and ADMIN roles
4. **Input Validation**: Jakarta Bean Validation on all DTOs
5. **CORS**: Configured for frontend integration
6. **Global Exception Handling**: Consistent error responses

## 📝 Validation Rules

### User Registration
- Name: 2-100 characters, required
- Email: Valid email format, required
- Password: Minimum 6 characters, required
- Role: USER (default) or ADMIN

### Task Creation/Update
- Title: 1-200 characters, required
- Description: Maximum 1000 characters, optional
- Status: PENDING, IN_PROGRESS, COMPLETED, or CANCELLED

## 🧪 Testing the API

### Using cURL

**Register:**
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

**Create Task (replace TOKEN with actual JWT):**
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{
    "title": "My First Task",
    "description": "Task description",
    "status": "PENDING"
  }'
```

## 🚀 Scalability Considerations

### Current Architecture
- **Monolithic Spring Boot Application**: Easy to deploy and maintain
- **RESTful API Design**: Stateless, scalable horizontally
- **JWT Stateless Authentication**: No server-side session storage
- **Database**: MySQL with JPA/Hibernate for ORM

### Recommended Scalability Improvements

1. **Caching Layer**
   - Implement Redis for:
     - JWT token blacklisting (logout)
     - Frequently accessed data (user profiles, tasks)
     - Rate limiting

2. **Database Optimization**
   - Add indexes on frequently queried fields (email, userId)
   - Implement database connection pooling
   - Consider read replicas for read-heavy operations

3. **Microservices Architecture** (Future)
   - Split into services:
     - Auth Service (authentication & authorization)
     - Task Service (task management)
     - User Service (user management)
   - Use API Gateway (Spring Cloud Gateway)
   - Service discovery (Eureka/Consul)

4. **Load Balancing**
   - Use Nginx or AWS ELB for distributing requests
   - Multiple application instances behind load balancer

5. **Message Queue**
   - Use RabbitMQ/Kafka for:
     - Async task processing
     - Event-driven architecture
     - Email notifications

6. **Monitoring & Logging**
   - Implement centralized logging (ELK Stack)
   - Application monitoring (Prometheus + Grafana)
   - Distributed tracing (Zipkin/Jaeger)

7. **Containerization**
   - Docker containers for easy deployment
   - Kubernetes for orchestration
   - CI/CD pipeline (Jenkins/GitHub Actions)

8. **API Rate Limiting**
   - Implement rate limiting per user/IP
   - Prevent abuse and ensure fair usage

## 📦 Dependencies

- Spring Boot 3.5.7
- Spring Security
- Spring Data JPA
- MySQL Connector
- JWT (JJWT 0.11.5)
- Lombok
- SpringDoc OpenAPI (Swagger)
- Jakarta Bean Validation

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists

2. **JWT Token Invalid**
   - Check token expiration (default: 24 hours)
   - Verify secret key in `application.properties`
   - Ensure token is sent in Authorization header

3. **CORS Error**
   - Verify frontend URL is in allowed origins
   - Check SecurityConfig CORS configuration

## 📄 License

This project is part of the PrimeTrade Assignment.

## 👤 Author

Developed as part of PrimeTrade Backend Developer Intern Assignment.

---

**Note**: This is a development version. For production, ensure:
- Strong JWT secret key
- HTTPS enabled
- Database credentials secured
- Proper logging and monitoring
- Rate limiting implemented

