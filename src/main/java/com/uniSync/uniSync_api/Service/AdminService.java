package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.AdminType;
import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.AdministerEntity;
import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Model.Faculty;
import com.uniSync.uniSync_api.Repository.AdminRepository;
import com.uniSync.uniSync_api.Repository.DepartmentRepository;
import com.uniSync.uniSync_api.Repository.FacultyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    // Create a new Admin
    public Admin createAdmin(Admin admin) {

        return adminRepository.save(admin);
    }

    // Create a Department and link it to an Admin and Faculty
    @Transactional
    public Department createDepartmentWithAdminAndFaculty(Department department, Admin admin, Faculty faculty) {
        department.setFaculty(faculty);
        if(admin.getAdminType().equals(AdminType.DEPARTMENT_ADMIN)){
            department.setAdmin(admin);
        }else{
            System.out.println("Incorrect Admin type");
        }
        return departmentRepository.save(department);
    }

    // Create a Faculty and link it to an Admin
    @Transactional
    public Faculty createFacultyWithAdmin(Faculty faculty, Admin admin) {
        if(admin.getAdminType().equals(AdminType.FACULTY_ADMIN)){
            faculty.setAdmin(admin); // From AdministerEntity superclass
        }else{
            System.out.println("Incorrect Admin type");
        }

        return facultyRepository.save(faculty);
    }

    // Get all Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Find Admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Delete Admin by ID
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
}

