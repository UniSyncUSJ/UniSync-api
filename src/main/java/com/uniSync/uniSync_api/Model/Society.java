package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "societies")
public class Society extends AdministerEntity{

    private String name;

    @ManyToMany(mappedBy = "societies")
    private Set<Student> students = new HashSet<>();

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Constructors
    public Society() { }

    public Society(String name, String description) {
        setName(name);
        setDescription(description);
    }
}
