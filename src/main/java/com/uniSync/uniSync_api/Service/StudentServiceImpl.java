package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Student;
import com.uniSync.uniSync_api.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByBatchYear(int batchYear) {
        Set<Student> students = studentRepository.findAllByBatchYear(batchYear);
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> getStudentsByDepartmentId(Long id) {
        Set<Student> students = studentRepository.findAllByDepartments_Id(id);
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> getStudentsByFacultyId(Long id) {
        Set<Student> students = studentRepository.findAllByFacultyId(id);
        return new ArrayList<>(students);
    }
}

