package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Optional<Student> getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
    List<Student> getStudentsByDepartmentId(Long departmentId);
    List<Student> getStudentsByFacultyId(Long facultyId);
    List<Student> getStudentsByBatchYear(int batchYear);
}

