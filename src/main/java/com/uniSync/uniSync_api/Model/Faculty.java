package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("FACULTY")
@Table(name = "faculties")
public class Faculty extends AdministerEntity {

    protected String name;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Student> students = new HashSet<Student>();

    @OneToMany(mappedBy = "faculty")
    private Set<Department> departments = new HashSet<Department>();

    //Setters & Getters
    public Long getId() {
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

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    // Constrcutors
    public Faculty() {}

    public Faculty(String name) {
        setName(name);
    }
}
