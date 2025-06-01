package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Department;
import com.uniSync.uniSync_api.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Optional<Department> getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    @Override
    public List<Department> getDepartmentsByFacultyId(Long id) {
        Set<Department> departments = departmentRepository.findAllByFacultyId(id);
        return new ArrayList<>(departments);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public boolean checkIfDepartmentExistsByName(String departmentName) {
        return departmentRepository.existsByName(departmentName);
    }
}
