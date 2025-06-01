package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Model.Faculty;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Department createDepartmentWithAdminAndFaculty(Department department, Admin admin, Faculty faculty);
    Faculty createFacultyWithAdmin(Faculty faculty, Admin admin);
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long id);
    void deleteAdminById(Long id);
}
