@echo off
REM UniSync API Test Script for Windows
REM This script tests all API endpoints in the correct order

echo === UniSync API Testing Script ===
echo Testing all available endpoints...
echo.

set BASE_URL=http://localhost:8080

echo 1. Testing Health Check Endpoint...
curl -s -o NUL -w "%%{http_code}" %BASE_URL%/ > temp_response.txt
set /p response=<temp_response.txt
if "%response%"=="200" (
    echo ✅ PASS: Health check endpoint
) else (
    echo ❌ FAIL: Health check endpoint ^(Got HTTP %response%^)
)

echo.
echo 2. Creating Test Admins...
curl -s -X POST %BASE_URL%/test/create-admins > admin_response.txt
curl -s -o NUL -w "%%{http_code}" -X POST %BASE_URL%/test/create-admins > admin_code.txt
set /p admin_http_code=<admin_code.txt

if "%admin_http_code%"=="200" (
    echo ✅ PASS: Create test admins
    echo    Check admin_response.txt for admin IDs
) else (
    echo ❌ FAIL: Create test admins ^(Got HTTP %admin_http_code%^)
)

echo.
echo 3. Testing Authentication...

REM Test valid login
curl -s -X POST %BASE_URL%/auth/login -H "Content-Type: application/x-www-form-urlencoded" -d "email=johndoe@example.com&password=john123" > login_response.txt
curl -s -o NUL -w "%%{http_code}" -X POST %BASE_URL%/auth/login -H "Content-Type: application/x-www-form-urlencoded" -d "email=johndoe@example.com&password=john123" > login_code.txt
set /p login_http_code=<login_code.txt

if "%login_http_code%"=="200" (
    echo ✅ PASS: Login with valid credentials
    echo    JWT Token saved to login_response.txt
) else (
    echo ❌ FAIL: Login with valid credentials ^(Got HTTP %login_http_code%^)
)

REM Test invalid login
curl -s -o NUL -w "%%{http_code}" -X POST %BASE_URL%/auth/login -H "Content-Type: application/x-www-form-urlencoded" -d "email=invalid@example.com&password=wrongpass" > invalid_login_code.txt
set /p invalid_login_code=<invalid_login_code.txt

if "%invalid_login_code%"=="401" (
    echo ✅ PASS: Login with invalid credentials ^(Expected 401^)
) else (
    echo ❌ FAIL: Login with invalid credentials ^(Got HTTP %invalid_login_code%, expected 401^)
)

echo.
echo 4. Testing Department Creation...

REM Test with sample admin IDs (you may need to adjust these based on your actual IDs)
curl -s -X POST %BASE_URL%/test/create-department-with-admin -H "Content-Type: application/x-www-form-urlencoded" -d "facultyAdminId=2&deptAdminId=1" > dept_response.txt
curl -s -o NUL -w "%%{http_code}" -X POST %BASE_URL%/test/create-department-with-admin -H "Content-Type: application/x-www-form-urlencoded" -d "facultyAdminId=2&deptAdminId=1" > dept_code.txt
set /p dept_http_code=<dept_code.txt

if "%dept_http_code%"=="200" (
    echo ✅ PASS: Create department with valid admin IDs
    echo    Response saved to dept_response.txt
) else (
    echo ❌ FAIL: Create department with valid admin IDs ^(Got HTTP %dept_http_code%^)
)

REM Test invalid admin ID
curl -s -o NUL -w "%%{http_code}" -X POST %BASE_URL%/test/create-department-with-admin -H "Content-Type: application/x-www-form-urlencoded" -d "facultyAdminId=999&deptAdminId=998" > invalid_dept_code.txt
set /p invalid_dept_code=<invalid_dept_code.txt

if "%invalid_dept_code%"=="400" (
    echo ✅ PASS: Create department with invalid admin IDs ^(Expected 400^)
) else (
    echo ❌ FAIL: Create department with invalid admin IDs ^(Got HTTP %invalid_dept_code%, expected 400^)
)

echo.
echo === Test Summary ===
echo All major endpoints have been tested.
echo Check the results above for any failures.
echo Response files created: admin_response.txt, login_response.txt, dept_response.txt
echo.
echo To run these tests with Postman:
echo 1. Import UniSync-API-Postman-Collection.json
echo 2. Import UniSync-Test-Environment.postman_environment.json
echo 3. Run the collection
echo.
echo To run with Newman CLI:
echo newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json

REM Clean up temporary files
del temp_response.txt admin_code.txt login_code.txt invalid_login_code.txt dept_code.txt invalid_dept_code.txt 2>NUL

pause
