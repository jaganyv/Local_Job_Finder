# Quick Start Guide

## Prerequisites Check
- ✅ Java 17+ installed: `java -version`
- ✅ Maven installed: `mvn -version`
- ✅ Node.js 16+ installed: `node -version`
- ✅ MySQL running: Check your MySQL service

## Step 1: Database Setup

1. Create MySQL database (or let Spring Boot create it automatically):
   ```sql
   CREATE DATABASE job_marketplace;
   ```

2. Update `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

## Step 2: Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will start on `http://localhost:8080`

## Step 3: Frontend Setup

Open a new terminal:

```bash
cd frontend
npm install
npm start
```

Frontend will start on `http://localhost:3000`

## Step 4: Test the Application

1. Open `http://localhost:3000` in your browser
2. Register a new account (choose SEEKER or EMPLOYER role)
3. If you registered as SEEKER:
   - You'll see the Job Seeker Dashboard
   - Browse jobs, apply, and save jobs
4. If you registered as EMPLOYER:
   - You'll see the Employer Dashboard
   - Post a job, view applications, accept/reject

## Creating an Admin Account

To create an admin account, you need to manually update the database:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

Or register normally and update via database.

## WhatsApp Notifications (Optional)

1. Sign up for UltraMSG API: https://ultramsg.com
2. Get your instance ID and token
3. Update `backend/src/main/resources/application.properties`:
   ```properties
   whatsapp.api.instance-id=your-instance-id
   whatsapp.api.token=your-token
   ```

## Troubleshooting

### Backend won't start
- Check MySQL is running
- Verify database credentials in `application.properties`
- Check port 8080 is not in use

### Frontend won't start
- Delete `node_modules` and run `npm install` again
- Check port 3000 is not in use
- Verify Node.js version is 16+

### CORS errors
- Ensure backend is running on port 8080
- Check `SecurityConfig.java` has correct CORS configuration

### Database connection errors
- Verify MySQL is running: `sudo systemctl status mysql`
- Check database exists
- Verify username/password in `application.properties`

## Default Test Accounts

You can create test accounts through the registration page:
- Job Seeker: Register with role "Job Seeker"
- Employer: Register with role "Employer"
- Admin: Register normally, then update role in database

## API Testing

You can test the API using:
- Postman
- curl commands
- The React frontend

Example curl for login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

