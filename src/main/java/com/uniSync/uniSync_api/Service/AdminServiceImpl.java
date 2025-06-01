package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.AdminType;
import com.uniSync.uniSync_api.Exceptions.InvalidAdminException;
import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Model.Faculty;
import com.uniSync.uniSync_api.Repository.AdminRepository;
import com.uniSync.uniSync_api.Repository.DepartmentRepository;
import com.uniSync.uniSync_api.Repository.FacultyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService{

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
        try{
            if(admin.getAdminType().equals(AdminType.DEPARTMENT_ADMIN)){
                department.setAdmin(admin);
            }else{
                throw new InvalidAdminException("Invalid Admin type");
            }
        }catch(InvalidAdminException e){
            e.printStackTrace();
        }
        return departmentRepository.save(department);
    }

    // Create a Faculty and link it to an Admin
    @Transactional
    public Faculty createFacultyWithAdmin(Faculty faculty, Admin admin) {
        try{
            if(admin.getAdminType().equals(AdminType.FACULTY_ADMIN)){
                faculty.setAdmin(admin);
            }else{
                throw new InvalidAdminException("Invalid Admin typr");
            }
        }catch(InvalidAdminException e){
            e.printStackTrace();
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

    @Override
    public List<Admin> getAdminsByType(AdminType adminType) {
        Set<Admin> admins = adminRepository.findAllByAdminType(adminType);
        return new ArrayList<>(admins);
    }

}

