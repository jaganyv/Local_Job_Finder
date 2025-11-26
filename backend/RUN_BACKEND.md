# How to Run the Backend

## Prerequisites
✅ Java 17+ (You have Java 21 - Perfect!)
✅ Maven 3.6+ (You have Maven 3.8.7 - Perfect!)
✅ MySQL Server running

## Step-by-Step Instructions

### 1. Ensure MySQL is Running
```bash
# Check if MySQL is running
sudo systemctl status mysql

# If not running, start it:
sudo systemctl start mysql
```

### 2. Navigate to Backend Directory
```bash
cd /home/jagan-yv/Cursor_ai/backend
```

### 3. Build the Project (First Time or After Changes)
```bash
mvn clean install
```

### 4. Run the Backend
```bash
mvn spring-boot:run
```

**OR** if you want to run it in the background:
```bash
mvn spring-boot:run &
```

### 5. Verify It's Running
- The backend will start on `http://localhost:8080`
- You should see Spring Boot startup logs
- Look for: "Started JobMarketplaceApplication in X.XXX seconds"

## Quick Start (All in One)
```bash
cd /home/jagan-yv/Cursor_ai/backend
mvn clean install
mvn spring-boot:run
```

## Alternative: Run JAR File (After Building)
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/job-marketplace-backend-1.0.0.jar
```

## Troubleshooting

### Port 8080 Already in Use
```bash
# Find process using port 8080
sudo lsof -i :8080

# Kill the process (replace PID with actual process ID)
kill -9 PID
```

### Database Connection Error
- Make sure MySQL is running: `sudo systemctl status mysql`
- Verify credentials in `application.properties`:
  - Username: root
  - Password: 9750
- Check if database exists or let Spring Boot create it automatically

### Maven Build Fails
```bash
# Clean and rebuild
mvn clean
mvn install
```

## Test the Backend

Once running, test with:
```bash
# Health check (if you add a health endpoint)
curl http://localhost:8080/auth/login

# Or open in browser
# http://localhost:8080
```

## Stop the Backend
- Press `Ctrl+C` in the terminal
- Or if running in background: `pkill -f spring-boot:run`

