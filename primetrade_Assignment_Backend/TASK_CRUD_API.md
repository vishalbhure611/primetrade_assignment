# Task CRUD API Documentation

## Overview

The Task CRUD API provides complete Create, Read, Update, and Delete operations for managing tasks. All endpoints require JWT authentication and support role-based access control (USER and ADMIN roles).

**Base URL**: `/api/v1/tasks`

## Authentication

All endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

## Role-Based Access Control

- **USER Role**: Can only access (create, read, update, delete) their own tasks
- **ADMIN Role**: Can access (create, read, update, delete) all tasks from all users

## API Endpoints

### 1. Create Task

Creates a new task for the authenticated user.

**Endpoint**: `POST /api/v1/tasks`

**Authentication**: Required

**Request Body**:
```json
{
  "title": "Complete assignment",
  "description": "Finish the PrimeTrade assignment",
  "status": "PENDING"
}
```

**Request Fields**:
- `title` (required): Task title (1-200 characters)
- `description` (optional): Task description (max 1000 characters)
- `status` (optional): Task status (PENDING, IN_PROGRESS, COMPLETED, CANCELLED). Defaults to PENDING if not provided.

**Response** (201 Created):
```json
{
  "message": "Task created successfully",
  "task": {
    "id": 1,
    "title": "Complete assignment",
    "description": "Finish the PrimeTrade assignment",
    "status": "PENDING",
    "userId": 1,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "title": "Complete assignment",
    "description": "Finish the PrimeTrade assignment",
    "status": "PENDING"
  }'
```

---

### 2. Get All Tasks

Retrieves all tasks. Regular users see only their own tasks, admins see all tasks.

**Endpoint**: `GET /api/v1/tasks`

**Authentication**: Required

**Query Parameters**: None

**Response** (200 OK):

**For Regular Users**:
```json
{
  "message": "Tasks retrieved successfully",
  "tasks": [
    {
      "id": 1,
      "title": "Complete assignment",
      "description": "Finish the PrimeTrade assignment",
      "status": "PENDING",
      "userId": 1,
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
  ],
  "count": 1
}
```

**For Admin Users**:
```json
{
  "message": "All tasks retrieved successfully",
  "tasks": [
    {
      "id": 1,
      "title": "Complete assignment",
      "description": "Finish the PrimeTrade assignment",
      "status": "PENDING",
      "userId": 1,
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    },
    {
      "id": 2,
      "title": "Review code",
      "description": "Review the pull request",
      "status": "IN_PROGRESS",
      "userId": 2,
      "createdAt": "2024-01-15T11:00:00",
      "updatedAt": "2024-01-15T11:30:00"
    }
  ],
  "count": 2
}
```

**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/v1/tasks \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 3. Get Task by ID

Retrieves a specific task by ID. Regular users can only access their own tasks, admins can access any task.

**Endpoint**: `GET /api/v1/tasks/{id}`

**Authentication**: Required

**Path Parameters**:
- `id` (required): Task ID

**Response** (200 OK):
```json
{
  "message": "Task retrieved successfully",
  "task": {
    "id": 1,
    "title": "Complete assignment",
    "description": "Finish the PrimeTrade assignment",
    "status": "PENDING",
    "userId": 1,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
}
```

**Error Response** (404 Not Found):
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Task not found or access denied"
}
```

**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/v1/tasks/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 4. Update Task

Updates an existing task. Regular users can only update their own tasks, admins can update any task.

**Endpoint**: `PUT /api/v1/tasks/{id}`

**Authentication**: Required

**Path Parameters**:
- `id` (required): Task ID

**Request Body** (all fields optional - partial update):
```json
{
  "title": "Updated task title",
  "description": "Updated description",
  "status": "IN_PROGRESS"
}
```

**Request Fields**:
- `title` (optional): Task title (1-200 characters)
- `description` (optional): Task description (max 1000 characters)
- `status` (optional): Task status (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)

**Response** (200 OK):
```json
{
  "message": "Task updated successfully",
  "task": {
    "id": 1,
    "title": "Updated task title",
    "description": "Updated description",
    "status": "IN_PROGRESS",
    "userId": 1,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T11:00:00"
  }
}
```

**Error Response** (404 Not Found):
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Task not found or access denied"
}
```

**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/v1/tasks/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "title": "Updated task title",
    "description": "Updated description",
    "status": "IN_PROGRESS"
  }'
```

---

### 5. Delete Task

Deletes a task by ID. Regular users can only delete their own tasks, admins can delete any task.

**Endpoint**: `DELETE /api/v1/tasks/{id}`

**Authentication**: Required

**Path Parameters**:
- `id` (required): Task ID

**Response** (200 OK):
```json
{
  "message": "Task deleted successfully"
}
```

**Error Response** (404 Not Found):
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Task not found or access denied"
}
```

**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/v1/tasks/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

## Task Status Values

The `status` field can have the following values:

- `PENDING` - Task is pending
- `IN_PROGRESS` - Task is in progress
- `COMPLETED` - Task is completed
- `CANCELLED` - Task is cancelled

**Default Status**: `PENDING` (if not specified during creation)

---

## Validation Rules

### Task Title
- **Required**: Yes
- **Min Length**: 1 character
- **Max Length**: 200 characters
- **Validation**: `@NotBlank`, `@Size(min = 1, max = 200)`

### Task Description
- **Required**: No
- **Max Length**: 1000 characters
- **Validation**: `@Size(max = 1000)`

### Task Status
- **Required**: No (defaults to PENDING)
- **Values**: PENDING, IN_PROGRESS, COMPLETED, CANCELLED
- **Type**: Enum

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "errors": {
    "title": "Title is required",
    "description": "Description must not exceed 1000 characters"
  }
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired JWT token"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Task not found or access denied"
}
```

---

## Example Workflow

### 1. Create a Task
```bash
POST /api/v1/tasks
{
  "title": "Learn Spring Boot",
  "description": "Complete Spring Boot tutorial",
  "status": "PENDING"
}
```

### 2. Get All Tasks
```bash
GET /api/v1/tasks
```

### 3. Get Specific Task
```bash
GET /api/v1/tasks/1
```

### 4. Update Task Status
```bash
PUT /api/v1/tasks/1
{
  "status": "IN_PROGRESS"
}
```

### 5. Update Task Completion
```bash
PUT /api/v1/tasks/1
{
  "status": "COMPLETED"
}
```

### 6. Delete Task
```bash
DELETE /api/v1/tasks/1
```

---

## Testing with Swagger UI

1. Start the backend application
2. Navigate to: `http://localhost:8080/swagger-ui/index.html`
3. Click on "Tasks" section
4. Click "Authorize" and enter your JWT token
5. Test all CRUD operations interactively

---

## Database Schema

### Tasks Table
```sql
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## Security Features

1. **JWT Authentication**: All endpoints require valid JWT token
2. **Role-Based Access**: USER and ADMIN roles with different permissions
3. **User Isolation**: Regular users can only access their own tasks
4. **Admin Privileges**: Admins can access all tasks
5. **Input Validation**: All inputs are validated before processing
6. **Error Handling**: Comprehensive error handling with meaningful messages

---

## Notes

- Tasks are automatically assigned to the authenticated user
- Task creation and update timestamps are automatically managed
- Admin users have full access to all tasks
- Regular users can only perform operations on their own tasks
- All endpoints support partial updates (only provided fields are updated)
- Task status defaults to PENDING if not specified during creation

---

## Support

For more information, refer to:
- **README.md** - General project documentation
- **ASSIGNMENT_CHECKLIST.md** - Assignment requirements
- **Swagger UI** - Interactive API documentation at `/swagger-ui/index.html`


