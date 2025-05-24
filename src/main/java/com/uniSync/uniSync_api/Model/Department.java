package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("DEPARTMENT")
@Table(name = "departments")
public class Department extends AdministerEntity {

    protected String name;

    @ManyToOne
    @JoinColumn(
            name = "faculty_id",
            nullable = false
    )
    private Faculty faculty;


    @ManyToMany(mappedBy = "departments")
    protected Set<Student> students = new HashSet<Student>();

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // Constructors
    public Department() {}

    public Department(String name,Faculty faculty) {
        setName(name);
        setFaculty(faculty);
    }
}
