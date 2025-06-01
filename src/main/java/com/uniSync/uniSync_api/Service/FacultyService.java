package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);
    List<Faculty> getAllFaculties();
    Optional<Faculty> getFacultyById(Long id);
    void deleteFaculty(Long id);
    Optional<Faculty> getFacultyByName(String name);
    boolean checkFacultyExistByName(String name);
}

