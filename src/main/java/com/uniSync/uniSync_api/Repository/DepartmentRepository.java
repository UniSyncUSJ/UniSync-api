package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findByName(String name);
    Set<Department> findAllByFacultyId(Long facultyId);
    boolean existsByName(String name);

}
