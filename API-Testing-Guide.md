# UniSync API Testing Guide

## Overview
This guide provides comprehensive testing for the UniSync API using Postman collection and Newman CLI tool.

## Files Created
- `UniSync-API-Postman-Collection.json` - Complete Postman collection with all endpoints
- `UniSync-Test-Environment.postman_environment.json` - Environment variables for testing

## Prerequisites
1. **Start the UniSync API server**:
   ```bash
   # In the project root directory
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

2. **Ensure PostgreSQL is running** with database `uniSync` on localhost:5432

## Import to Postman

### Method 1: Using Postman GUI
1. Open Postman
2. Click "Import" in the top left
3. Select "Upload Files" and choose `UniSync-API-Postman-Collection.json`
4. Import the environment file `UniSync-Test-Environment.postman_environment.json`
5. Select the "UniSync Test Environment" in the environment dropdown

### Method 2: Using Newman CLI
Install Newman globally:
```bash
npm install -g newman
```

Run the complete test suite:
```bash
newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json
```

## Test Execution Order

### 1. Basic Health Check
- **GET /** - Verify API is running
- Expected: "Uni-Sync" response

### 2. Setup Test Data
- **POST /test/create-admins** - Create test admin users
- This creates:
  - John Doe (johndoe@example.com) - Department Admin
  - John Adams (johnadams@example.com) - Faculty Admin
- Both users have password: `john123`

### 3. Authentication Tests
- **POST /auth/login** - Test login with valid credentials
- **POST /auth/login** - Test login with invalid credentials
- **POST /auth/login** - Test login with missing parameters

### 4. Department Creation Tests
- **POST /test/create-department-with-admin** - Create department with valid admin IDs
- **POST /test/create-department-with-admin** - Test with invalid admin IDs
- **POST /test/create-department-with-admin** - Test with missing parameters

### 5. Integration Tests
- **POST /auth/login** - Login after creating users through test endpoints

## Test Scenarios Covered

### Authentication Endpoint (`/auth/login`)
- ✅ Valid credentials
- ✅ Invalid email
- ✅ Invalid password
- ✅ Missing email parameter
- ✅ Missing password parameter
- ✅ Non-existent user

### Test Endpoints
- ✅ Admin creation
- ✅ Department creation with valid admin IDs
- ✅ Department creation with invalid faculty admin ID
- ✅ Department creation with invalid department admin ID
- ✅ Department creation with missing parameters

### Health Check
- ✅ Basic API connectivity

## Expected Test Results

### Successful Tests
- **GET /** → Status 200, Response: "Uni-Sync"
- **POST /test/create-admins** → Status 200, Response contains admin IDs
- **POST /auth/login** (valid) → Status 200, Response contains JWT token
- **POST /test/create-department-with-admin** (valid) → Status 200, Department created

### Error Tests
- **POST /auth/login** (invalid) → Status 401, "Invalid credentials"
- **POST /test/create-department-with-admin** (invalid ID) → Status 400, "not found"

## Environment Variables
The following variables are automatically set during test execution:
- `base_url` - API base URL (http://localhost:8080)
- `admin_id_1` - First admin ID (set after create-admins)
- `admin_id_2` - Second admin ID (set after create-admins)
- `jwt_token` - JWT token (set after successful login)

## Manual Testing with cURL

### 1. Test Health Check
```bash
curl -X GET http://localhost:8080/
```

### 2. Create Test Admins
```bash
curl -X POST http://localhost:8080/test/create-admins
```

### 3. Login Test
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=johndoe@example.com&password=john123"
```

### 4. Create Department (replace IDs with actual values from step 2)
```bash
curl -X POST http://localhost:8080/test/create-department-with-admin \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "facultyAdminId=2&deptAdminId=1"
```

## Database State
After running all tests, your PostgreSQL database will contain:
- 2 Users (John Doe, John Adams)
- 2 Admins (Department Admin, Faculty Admin)
- 1 Faculty (Science Faculty)
- 1 Department (Computer Science)

## Troubleshooting

### Common Issues
1. **Connection Error**: Ensure the API server is running on port 8080
2. **Database Error**: Verify PostgreSQL is running and accessible
3. **Authentication Error**: Ensure users are created before testing login
4. **Invalid Admin ID**: Run create-admins endpoint first

### Reset Database
The application uses `spring.jpa.hibernate.ddl-auto=create-drop`, so restarting the application will reset the database.

## Advanced Testing

### Load Testing with Newman
```bash
# Run tests multiple times
newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json -n 5

# Generate detailed report
newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json -r htmlextra
```

### CI/CD Integration
The collection can be integrated into CI/CD pipelines using Newman:
```yaml
# Example GitHub Actions step
- name: Run API Tests
  run: |
    npm install -g newman
    newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json --reporters cli,json --reporter-json-export results.json
```

## Security Considerations
- Test credentials are hardcoded for testing purposes only
- In production, use proper authentication and authorization
- Consider using test-specific database
- Rotate test credentials regularly

## Future Enhancements
- Add more comprehensive error scenarios
- Include performance testing
- Add data validation tests
- Implement proper test cleanup
- Add API documentation generation
