package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Common.AdminType;
import com.uniSync.uniSync_api.Common.UserRole;
import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Model.Faculty;
import com.uniSync.uniSync_api.Model.User;
import com.uniSync.uniSync_api.Repository.AdminRepository;
import com.uniSync.uniSync_api.Repository.UserRepository;
import com.uniSync.uniSync_api.Service.AdminServiceImpl;
import com.uniSync.uniSync_api.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/create-test-user")
    public ResponseEntity<String> createTestUser() {
        User user = new User(
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            UserRole.STUDENT,
            "1234567890"
        );
        user = userRepository.save(user);
        return ResponseEntity.ok("Test user created successfully with ID: " + user.getId());
    }

    @GetMapping("/verify-user/{email}")
    public ResponseEntity<?> verifyUser(@PathVariable String email) {
        User user = userRepository.findAll().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);

        if (user == null) {
            return ResponseEntity.ok("User not found");
        }

        return ResponseEntity.ok(String.format(
            "User found:\nID: %d\nEmail: %s\nPassword Hash: %s\nRole: %s",
            user.getId(),
            user.getEmail(),
            user.getPasswordHash(),
            user.getRole()
        ));
    }

    @PostMapping("/create-admins")
    public ResponseEntity<String> createAdmin() {
        User user = new User("John", "Doe", "johndoe@example.com", "john123", UserRole.ADMIN, "0744561619");
        user = userRepository.save(user);

        User user2 = new User("John","Adams","johnadams@example.com","john123",UserRole.ADMIN,"0784652312");
        user2 = userRepository.save(user2);

        Admin admin1 = new Admin();
        admin1.setUser(user);
        admin1.setAdminType(AdminType.DEPARTMENT_ADMIN);
        admin1 = adminService.createAdmin(admin1);

        Admin admin2 = new Admin();
        admin2.setUser(user2);
        admin2.setAdminType(AdminType.FACULTY_ADMIN);
        admin2 = adminService.createAdmin(admin2);

        return ResponseEntity.ok("Admins created successfully with ID: \n" + admin1.getUser().getId()+"\n"+admin2.getUser().getId());
    }

    @PostMapping("/create-department-with-admin")
    public ResponseEntity<String> createDepartmentWithAdmin(@RequestParam Long facultyAdminId, @RequestParam Long deptAdminId) {
        Optional<Admin> optionalAdmin = adminRepository.findById(deptAdminId);
        if (optionalAdmin.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin with ID " + deptAdminId + " not found.");
        }

        Admin deptAdmin = optionalAdmin.get();

        Optional<Admin> opAdmin2 = adminRepository.findById(facultyAdminId);
        if (opAdmin2.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin with ID " + facultyAdminId+ " not found.");
        }
        Admin facultyAdmin = opAdmin2.get();

        Faculty faculty = new Faculty();
        faculty.setName("Science Faculty");
        faculty = adminService.createFacultyWithAdmin(faculty, facultyAdmin);

        Department department = new Department();
        department.setName("Computer Science");
        department.setFaculty(faculty);

//        if(deptAdmin.getAdminType().equals(AdminType.DEPARTMENT_ADMIN)){
//            department.setAdmin(deptAdmin);
//        }else{
//            System.out.println("Incorrect Admin type");
//        }


        department = adminService.createDepartmentWithAdminAndFaculty(department, deptAdmin, faculty);

        return ResponseEntity.ok("Department created under Admin ID: " + deptAdmin.getUser().getId() +
                ", Department ID: " + department.getId());
    }

}

