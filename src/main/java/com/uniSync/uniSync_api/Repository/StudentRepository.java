package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Student;
import com.uniSync.uniSync_api.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Set<Student> findAllByDepartments_Id(Long departmentId);
    Set<Student> findAllByFacultyId(Long facultyId);
    Set<Student> findAllByBatchYear(int year);
    Set<Student> findByEvents_Id(Long eventId);
    Set<Student> findByFacultyId(Long facultyId);
    Set<Student> findByDepartments_Id(Long departmentId);
    Set<Student> findBySocieties_Id(Long societyId);

}
