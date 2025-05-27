package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Common.AdminType;
import com.uniSync.uniSync_api.Common.UserRole;
import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Model.Faculty;
import com.uniSync.uniSync_api.Model.User;
import com.uniSync.uniSync_api.Repository.UserRepository;
import com.uniSync.uniSync_api.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-admin-department")
    public ResponseEntity<String> createAdminAndDepartment() {

        User user = new User("John","Doe","johndoe@example.com","john123", UserRole.ADMIN,"0744561619");
        user = userRepository.save(user);

        if (user == null) {
            return ResponseEntity.badRequest().body("User with ID 1 not found");
        }

        // Create Admin linked to User
        Admin admin = new Admin();
        admin.setUser(user);
        admin.setAdminType(AdminType.DEPARTMENT_ADMIN);
        admin = adminService.createAdmin(admin);

        // Create Faculty (no admin linked here for simplicity)
        Faculty faculty = new Faculty();
        faculty.setName("Science Faculty");

        // Save Faculty (you might want a FacultyService or repo, here just quick)
        faculty = adminService.createFacultyWithAdmin(faculty, admin);

        // Create Department linked to Faculty and Admin
        Department department = new Department();
        department.setName("Computer Science");
        department.setFaculty(faculty);
        department.setAdmin(admin);

        department = adminService.createDepartmentWithAdminAndFaculty(department, admin, faculty);

        return ResponseEntity.ok("Admin and Department created successfully with ID: " + department.getId());
    }
}
