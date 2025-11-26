# Job Marketplace - Full Stack Application

A comprehensive job marketplace application built with Spring Boot (Backend) and React (Frontend), featuring location-based job search, JWT authentication, and WhatsApp notifications.

## 🚀 Features

### For Job Seekers
- ✅ User registration and login
- ✅ View nearby jobs with automatic location detection
- ✅ Filter jobs by type (part-time/full-time), salary range, and hours
- ✅ Apply for jobs
- ✅ Track application status (Pending/Accepted/Rejected)
- ✅ Save jobs for later

### For Employers
- ✅ User registration and login
- ✅ Post job opportunities with details
- ✅ Edit and close jobs
- ✅ View applicants for each job
- ✅ Accept/reject applicants
- ✅ WhatsApp notifications sent automatically when accepting applicants

### For Admins
- ✅ Approve suspicious job posts
- ✅ Delete fake accounts
- ✅ View platform analytics (users, jobs, applications)

## 🛠️ Tech Stack

### Backend
- **Java 17** + **Spring Boot 3.2.0**
- **Spring Web** - REST API
- **Spring Data JPA** - Database operations
- **Spring Security** - JWT Authentication
- **MySQL** - Database
- **JWT (jjwt)** - Token-based authentication
- **UltraMSG API** - WhatsApp notifications

### Frontend
- **React 18** with **TypeScript**
- **Tailwind CSS** - Styling
- **React Router** - Navigation
- **Axios** - HTTP client

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 16+ and npm
- MySQL 8.0+
- UltraMSG account (for WhatsApp notifications) - Optional

## 🔧 Setup Instructions

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Configure database in `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/job_marketplace?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Configure WhatsApp API (Optional):**
   ```properties
   whatsapp.api.instance-id=your-instance-id
   whatsapp.api.token=your-token
   ```

4. **Build and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   Backend will run on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm start
   ```

   Frontend will run on `http://localhost:3000`

## 📁 Project Structure

```
job-marketplace/
├── backend/
│   ├── src/main/java/com/jobmarketplace/
│   │   ├── controller/     # REST Controllers
│   │   ├── service/        # Business Logic
│   │   ├── repository/     # Data Access Layer
│   │   ├── model/          # Entity Models
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── security/       # Security Configuration
│   │   └── util/           # Utility Classes
│   └── src/main/resources/
│       └── application.properties
│
└── frontend/
    ├── src/
    │   ├── components/     # Reusable Components
    │   ├── pages/          # Page Components
    │   ├── context/        # React Context
    │   ├── utils/          # Utility Functions
    │   └── types/          # TypeScript Types
    └── public/
```

## 🔑 API Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user

### Jobs
- `GET /jobs/all` - Get all active jobs
- `GET /jobs/nearby?lat={lat}&lon={lon}&radius={radius}` - Get nearby jobs
- `POST /jobs/filter` - Filter jobs
- `GET /jobs/{id}` - Get job by ID
- `POST /jobs/post` - Post new job (Employer only)
- `PUT /jobs/{id}` - Update job (Employer only)
- `DELETE /jobs/{id}` - Close job (Employer only)
- `GET /jobs/my-jobs` - Get my posted jobs (Employer only)

### Applications
- `POST /applications/apply/{jobId}` - Apply for job
- `GET /applications/my-applications` - Get my applications
- `GET /applications/job/{jobId}` - Get applications for a job (Employer only)
- `PUT /applications/{id}/accept` - Accept application (Employer only)
- `PUT /applications/{id}/reject` - Reject application (Employer only)

### Saved Jobs
- `POST /saved-jobs/{jobId}` - Save job
- `GET /saved-jobs/my-saved` - Get saved jobs
- `DELETE /saved-jobs/{jobId}` - Unsave job

### Admin
- `PUT /admin/jobs/{id}/approve` - Approve job
- `DELETE /admin/users/{id}` - Delete user
- `GET /admin/jobs/pending` - Get pending jobs
- `GET /admin/analytics` - Get platform analytics

## 🌟 Key Features Implementation

### 1. Nearby Job Search (Haversine Formula)
The backend uses the Haversine formula to calculate distances between user location and job locations:

```sql
SELECT *, (6371 * acos(
  cos(radians(:lat)) * cos(radians(location_lat)) *
  cos(radians(location_lon) - radians(:lon)) +
  sin(radians(:lat)) * sin(radians(location_lat))
)) AS distance
FROM jobs
HAVING distance < 10;
```

### 2. JWT Authentication
- Tokens are generated on login/register
- Tokens are stored in localStorage on frontend
- All protected routes require valid JWT token
- Token expiration: 24 hours (configurable)

### 3. WhatsApp Notifications
When an employer accepts an application, a WhatsApp notification is automatically sent to the job seeker using UltraMSG API.

## 🗄️ Database Schema

### Users Table
- `user_id` (PK)
- `name`
- `email` (unique)
- `password` (encrypted)
- `role` (SEEKER/EMPLOYER/ADMIN)
- `phone`
- `location_lat`
- `location_lon`

### Jobs Table
- `job_id` (PK)
- `employer_id` (FK)
- `title`
- `description`
- `salary`
- `job_type` (PART_TIME/FULL_TIME)
- `created_at`
- `is_active`
- `is_approved`
- `location_lat`
- `location_lon`

### Applications Table
- `application_id` (PK)
- `job_id` (FK)
- `seeker_id` (FK)
- `applied_at`
- `status` (PENDING/ACCEPTED/REJECTED)

### Saved Jobs Table
- `saved_job_id` (PK)
- `seeker_id` (FK)
- `job_id` (FK)
- `saved_at`

## 🚢 Deployment

### Backend Deployment (Render/Railway/AWS EC2)
1. Build JAR: `mvn clean package`
2. Upload JAR file
3. Set environment variables
4. Run: `java -jar job-marketplace-backend.jar`

### Frontend Deployment (Vercel/Netlify)
1. Build: `npm run build`
2. Deploy `build` folder
3. Update API URL in `src/utils/api.ts`

### Database (Railway/Neon/AWS RDS)
1. Create MySQL instance
2. Update connection string in `application.properties`

## 📝 Notes

- Default admin account needs to be created manually in the database
- WhatsApp notifications require valid UltraMSG credentials
- Location services require user permission in browser
- All passwords are encrypted using BCrypt

## 🎯 Future Enhancements

- Resume upload and management
- Email notifications
- Advanced search with keywords
- Job recommendations based on user profile
- Rating and review system
- Payment integration for premium features

## 📄 License

This project is open source and available for educational purposes.

