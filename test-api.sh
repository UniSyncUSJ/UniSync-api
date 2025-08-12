#!/bin/bash

# UniSync API Test Script
# This script tests all API endpoints in the correct order

echo "=== UniSync API Testing Script ==="
echo "Testing all available endpoints..."
echo

BASE_URL="http://localhost:8080"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print test results
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ PASS${NC}: $2"
    else
        echo -e "${RED}❌ FAIL${NC}: $2"
    fi
}

# Function to extract admin IDs from response
extract_admin_ids() {
    local response="$1"
    ADMIN_ID_1=$(echo "$response" | grep -o '[0-9]\+' | sed -n '1p')
    ADMIN_ID_2=$(echo "$response" | grep -o '[0-9]\+' | sed -n '2p')
}

echo "1. Testing Health Check Endpoint..."
response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/")
if [ "$response" -eq 200 ]; then
    print_result 0 "Health check endpoint"
else
    print_result 1 "Health check endpoint (Got HTTP $response)"
fi

echo
echo "2. Creating Test Admins..."
admin_response=$(curl -s -X POST "$BASE_URL/test/create-admins")
admin_http_code=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/test/create-admins")

if [ "$admin_http_code" -eq 200 ]; then
    print_result 0 "Create test admins"
    extract_admin_ids "$admin_response"
    echo "   Admin ID 1: $ADMIN_ID_1"
    echo "   Admin ID 2: $ADMIN_ID_2"
else
    print_result 1 "Create test admins (Got HTTP $admin_http_code)"
fi

echo
echo "3. Testing Authentication..."

# Test valid login
login_response=$(curl -s -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "email=johndoe@example.com&password=john123")
login_http_code=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "email=johndoe@example.com&password=john123")

if [ "$login_http_code" -eq 200 ]; then
    print_result 0 "Login with valid credentials"
    echo "   JWT Token: ${login_response:0:50}..."
else
    print_result 1 "Login with valid credentials (Got HTTP $login_http_code)"
fi

# Test invalid login
invalid_login_code=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "email=invalid@example.com&password=wrongpass")

if [ "$invalid_login_code" -eq 401 ]; then
    print_result 0 "Login with invalid credentials (Expected 401)"
else
    print_result 1 "Login with invalid credentials (Got HTTP $invalid_login_code, expected 401)"
fi

echo
echo "4. Testing Department Creation..."

if [ -n "$ADMIN_ID_1" ] && [ -n "$ADMIN_ID_2" ]; then
    dept_response=$(curl -s -X POST "$BASE_URL/test/create-department-with-admin" \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "facultyAdminId=$ADMIN_ID_2&deptAdminId=$ADMIN_ID_1")
    dept_http_code=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/test/create-department-with-admin" \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "facultyAdminId=$ADMIN_ID_2&deptAdminId=$ADMIN_ID_1")

    if [ "$dept_http_code" -eq 200 ]; then
        print_result 0 "Create department with valid admin IDs"
        echo "   Response: $dept_response"
    else
        print_result 1 "Create department with valid admin IDs (Got HTTP $dept_http_code)"
    fi
else
    print_result 1 "Create department with valid admin IDs (Admin IDs not available)"
fi

# Test invalid admin ID
invalid_dept_code=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/test/create-department-with-admin" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "facultyAdminId=999&deptAdminId=998")

if [ "$invalid_dept_code" -eq 400 ]; then
    print_result 0 "Create department with invalid admin IDs (Expected 400)"
else
    print_result 1 "Create department with invalid admin IDs (Got HTTP $invalid_dept_code, expected 400)"
fi

echo
echo "=== Test Summary ==="
echo "All major endpoints have been tested."
echo "Check the results above for any failures."
echo
echo "To run these tests with Postman:"
echo "1. Import UniSync-API-Postman-Collection.json"
echo "2. Import UniSync-Test-Environment.postman_environment.json"
echo "3. Run the collection"
echo
echo "To run with Newman CLI:"
echo "newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json"
