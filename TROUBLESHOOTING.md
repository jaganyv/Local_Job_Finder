# Troubleshooting Registration Issues

## Common Issues and Solutions

### 1. Backend Not Running
**Symptom:** "Network Error" or "Connection Refused"

**Solution:**
```bash
cd backend
mvn spring-boot:run
```

Check if backend is running on `http://localhost:8080`

### 2. Database Connection Error
**Symptom:** "Internal Server Error" or database-related errors in backend logs

**Solution:**
- Ensure MySQL is running: `sudo systemctl status mysql`
- Check database credentials in `backend/src/main/resources/application.properties`
- Verify database exists or let Spring Boot create it automatically

### 3. CORS Error
**Symptom:** "CORS policy" error in browser console

**Solution:**
- Verify `SecurityConfig.java` has CORS configured for `http://localhost:3000`
- Check backend is running on port 8080
- Restart both frontend and backend

### 4. Email Already Exists
**Symptom:** "Email already exists" error

**Solution:**
- Use a different email address
- Or delete the existing user from database:
  ```sql
  DELETE FROM users WHERE email = 'your-email@example.com';
  ```

### 5. Validation Errors
**Symptom:** "Validation failed" or field-specific errors

**Solution:**
- Ensure all required fields are filled:
  - Name (required)
  - Email (required, valid email format)
  - Password (required)
- Phone and location are optional

### 6. Role Enum Issue
**Symptom:** "Invalid role" or enum conversion error

**Solution:**
- Ensure role is sent as: `"SEEKER"` or `"EMPLOYER"` (uppercase)
- Check browser network tab to see what's being sent

## Debugging Steps

### Step 1: Check Backend Logs
```bash
cd backend
mvn spring-boot:run
```
Look for errors in the console output.

### Step 2: Check Browser Console
1. Open browser DevTools (F12)
2. Go to Console tab - look for JavaScript errors
3. Go to Network tab - check the registration request:
   - Status code (should be 200)
   - Request payload
   - Response body

### Step 3: Test API Directly
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "role": "SEEKER"
  }'
```

### Step 4: Check Database
```bash
mysql -u root -p
USE job_marketplace;
SELECT * FROM users;
```

## Quick Fixes

### Restart Everything
```bash
# Terminal 1: Backend
cd backend
mvn spring-boot:run

# Terminal 2: Frontend
cd frontend
npm start
```

### Clear Browser Cache
- Hard refresh: Ctrl+Shift+R (Linux/Windows) or Cmd+Shift+R (Mac)
- Or clear browser cache and cookies

### Check Ports
```bash
# Check if ports are in use
sudo lsof -i :8080  # Backend
sudo lsof -i :3000  # Frontend
```

## Still Not Working?

1. Check the exact error message in:
   - Browser console (F12)
   - Backend terminal output
   - Network tab in browser DevTools

2. Verify:
   - Backend is running and accessible at http://localhost:8080
   - Frontend is running at http://localhost:3000
   - MySQL is running
   - Database credentials are correct

3. Try a simple test:
   ```bash
   curl http://localhost:8080/auth/login
   ```
   Should return an error (expected), but confirms backend is running.

