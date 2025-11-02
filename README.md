
# Library Management System (Spring Boot + Security + MySQL)

## Quick start

1. Create a MySQL database:
   ```sql
   CREATE DATABASE library_db;
   ```
2. Update `src/main/resources/application.properties` with your MySQL username/password.
3. Build and run with Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Use Postman to test endpoints:
   - `POST /api/auth/register` to create user
   - `POST /api/auth/login` to login and receive JWT
   - Add Authorization header `Bearer <token>` for protected endpoints

Default admin user (from data.sql):
- username: admin
- password: admin

