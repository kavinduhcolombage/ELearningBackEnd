# 📚 ELearning System API

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Latest-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-blue)
![License](https://img.shields.io/badge/license-MIT-brightgreen)

A comprehensive e-learning platform REST API built with Spring Boot, featuring role-based authentication and secure user management.

---

## ✨ Features

### 👥 User Management
- Multi-role system (Student, Instructor, Admin)
- Secure user registration and authentication
- Profile management capabilities
- Password encryption
- Role-based access control

### 🔐 Security
- JWT-based authentication
- Encrypted password storage using BCrypt
- Role-based resource access
- Data privacy protection

---

## 🛠️ Tech Stack

- **Framework:** Spring Boot
- **Security:** Spring Security with JWT
- **Database:** MySQL
- **Build Tool:** Maven/Gradle

---

## 📋 Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+ or Gradle 7+

---

## 🚀 Getting Started

### 1️⃣ Database Setup
```sql
CREATE DATABASE elearn;
```

### 2️⃣ Clone Repository
```bash
git clone https://github.com/kavinduhcolombage/ELearningBackEnd.git
cd ELearningBackEnd
```

### 3️⃣ Configuration
Create `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/elearn
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔄 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | User login |
| POST | `/api/v1/auth/refresh` | Refresh token |

### User Operations
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users/profile` | Get profile |
| PUT | `/api/v1/users/update_profile` | Update profile |
| DELETE | `/api/v1/users/delete_profile` | Delete account |

### Admin Operations
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/admin/userlist` | List all users |
| POST | `/api/v1/admin/create` | create users |
| PUT | `/api/v1/admin/{id}` | Get user |

---

## 🔒 Security Implementation

- JWT-based authentication
- BCrypt password encryption
- Role-based access control
- Secure endpoints
- CORS configuration

---

## ⚠️ Error Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Server Error |

---

## 🤝 Contributing

1. Fork repository
2. Create feature branch
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit changes
   ```bash
   git commit -m 'Add YourFeature'
   ```
4. Push to branch
   ```bash
   git push origin feature/YourFeature
   ```
5. Open Pull Request

---

## 📝 License

MIT License - see [LICENSE.md](LICENSE.md)

---

## 📞 Support

- Create an issue
- Contact development team
- Check documentation

---

## 🔄 API Response Examples

### User Registration
```json
// Request
POST /api/auth/register
{
  "username": "student1",
  "email": "student1@example.com",
  "password": "securepass",
  "role": "STUDENT"
}

// Response
{
  "id": 1,
  "username": "student1",
  "email": "student1@example.com",
  "role": "STUDENT",
  "created": "2024-02-07T10:30:00Z"
}
```

### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "expiresIn": 3600
}
```

---

*Kavindu Hansana*
