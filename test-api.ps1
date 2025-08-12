# UniSync API Test Script for PowerShell
# This script tests all API endpoints in the correct order

Write-Host "=== UniSync API Testing Script ===" -ForegroundColor Yellow
Write-Host "Testing all available endpoints..." -ForegroundColor Yellow
Write-Host

$BaseUrl = "http://localhost:8080"

# Function to print test results
function Print-Result {
    param(
        [bool]$Success,
        [string]$TestName,
        [string]$Details = ""
    )
    
    if ($Success) {
        Write-Host "✅ PASS: $TestName" -ForegroundColor Green
        if ($Details) {
            Write-Host "   $Details" -ForegroundColor Gray
        }
    } else {
        Write-Host "❌ FAIL: $TestName" -ForegroundColor Red
        if ($Details) {
            Write-Host "   $Details" -ForegroundColor Gray
        }
    }
}

# Function to make HTTP requests
function Invoke-ApiRequest {
    param(
        [string]$Method = "GET",
        [string]$Url,
        [hashtable]$Headers = @{},
        [string]$Body = $null,
        [string]$ContentType = "application/json"
    )
    
    try {
        $response = Invoke-WebRequest -Uri $Url -Method $Method -Headers $Headers -Body $Body -ContentType $ContentType -UseBasicParsing
        return @{
            StatusCode = $response.StatusCode
            Content = $response.Content
            Success = $true
        }
    } catch {
        return @{
            StatusCode = $_.Exception.Response.StatusCode.Value__
            Content = $_.Exception.Message
            Success = $false
        }
    }
}

Write-Host "1. Testing Health Check Endpoint..." -ForegroundColor Cyan
$healthResponse = Invoke-ApiRequest -Url "$BaseUrl/"
$healthSuccess = $healthResponse.StatusCode -eq 200
Print-Result -Success $healthSuccess -TestName "Health check endpoint" -Details "HTTP $($healthResponse.StatusCode)"

Write-Host
Write-Host "2. Creating Test Admins..." -ForegroundColor Cyan
$adminResponse = Invoke-ApiRequest -Method "POST" -Url "$BaseUrl/test/create-admins"
$adminSuccess = $adminResponse.StatusCode -eq 200
Print-Result -Success $adminSuccess -TestName "Create test admins" -Details "HTTP $($adminResponse.StatusCode)"

if ($adminSuccess) {
    # Extract admin IDs from response
    $adminIds = [regex]::Matches($adminResponse.Content, '\d+')
    if ($adminIds.Count -ge 2) {
        $AdminId1 = $adminIds[0].Value
        $AdminId2 = $adminIds[1].Value
        Write-Host "   Admin ID 1: $AdminId1" -ForegroundColor Gray
        Write-Host "   Admin ID 2: $AdminId2" -ForegroundColor Gray
    }
}

Write-Host
Write-Host "3. Testing Authentication..." -ForegroundColor Cyan

# Test valid login
$loginBody = "email=johndoe@example.com&password=john123"
$loginResponse = Invoke-ApiRequest -Method "POST" -Url "$BaseUrl/auth/login" -Body $loginBody -ContentType "application/x-www-form-urlencoded"
$loginSuccess = $loginResponse.StatusCode -eq 200
Print-Result -Success $loginSuccess -TestName "Login with valid credentials" -Details "HTTP $($loginResponse.StatusCode)"

if ($loginSuccess) {
    $jwtToken = $loginResponse.Content
    Write-Host "   JWT Token: $($jwtToken.Substring(0, [Math]::Min(50, $jwtToken.Length)))..." -ForegroundColor Gray
}

# Test invalid login
$invalidLoginBody = "email=invalid@example.com&password=wrongpass"
$invalidLoginResponse = Invoke-ApiRequest -Method "POST" -Url "$BaseUrl/auth/login" -Body $invalidLoginBody -ContentType "application/x-www-form-urlencoded"
$invalidLoginSuccess = $invalidLoginResponse.StatusCode -eq 401
Print-Result -Success $invalidLoginSuccess -TestName "Login with invalid credentials (Expected 401)" -Details "HTTP $($invalidLoginResponse.StatusCode)"

Write-Host
Write-Host "4. Testing Department Creation..." -ForegroundColor Cyan

if ($AdminId1 -and $AdminId2) {
    $deptBody = "facultyAdminId=$AdminId2&deptAdminId=$AdminId1"
    $deptResponse = Invoke-ApiRequest -Method "POST" -Url "$BaseUrl/test/create-department-with-admin" -Body $deptBody -ContentType "application/x-www-form-urlencoded"
    $deptSuccess = $deptResponse.StatusCode -eq 200
    Print-Result -Success $deptSuccess -TestName "Create department with valid admin IDs" -Details "HTTP $($deptResponse.StatusCode)"
    
    if ($deptSuccess) {
        Write-Host "   Response: $($deptResponse.Content)" -ForegroundColor Gray
    }
} else {
    Print-Result -Success $false -TestName "Create department with valid admin IDs" -Details "Admin IDs not available"
}

# Test invalid admin ID
$invalidDeptBody = "facultyAdminId=999&deptAdminId=998"
$invalidDeptResponse = Invoke-ApiRequest -Method "POST" -Url "$BaseUrl/test/create-department-with-admin" -Body $invalidDeptBody -ContentType "application/x-www-form-urlencoded"
$invalidDeptSuccess = $invalidDeptResponse.StatusCode -eq 400
Print-Result -Success $invalidDeptSuccess -TestName "Create department with invalid admin IDs (Expected 400)" -Details "HTTP $($invalidDeptResponse.StatusCode)"

Write-Host
Write-Host "=== Test Summary ===" -ForegroundColor Yellow
Write-Host "All major endpoints have been tested." -ForegroundColor Yellow
Write-Host "Check the results above for any failures." -ForegroundColor Yellow
Write-Host
Write-Host "To run these tests with Postman:" -ForegroundColor Cyan
Write-Host "1. Import UniSync-API-Postman-Collection.json" -ForegroundColor White
Write-Host "2. Import UniSync-Test-Environment.postman_environment.json" -ForegroundColor White
Write-Host "3. Run the collection" -ForegroundColor White
Write-Host
Write-Host "To run with Newman CLI:" -ForegroundColor Cyan
Write-Host "newman run UniSync-API-Postman-Collection.json -e UniSync-Test-Environment.postman_environment.json" -ForegroundColor White

Read-Host "Press Enter to exit"
