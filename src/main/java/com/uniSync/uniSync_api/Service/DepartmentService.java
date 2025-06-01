package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
    Optional<Department> getDepartmentById(Long id);
    Optional<Department> getDepartmentByName(String name);
    List<Department> getDepartmentsByFacultyId(Long id);
    void deleteDepartment(Long id);
    boolean checkIfDepartmentExistsByName(String departmentName);
}
