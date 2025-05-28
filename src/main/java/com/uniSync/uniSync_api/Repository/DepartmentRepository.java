package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {


}
