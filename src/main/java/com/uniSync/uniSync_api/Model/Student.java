package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "student_department",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<Department>();

    @ManyToOne
    @JoinColumn(
            name = "faculty_id",
            nullable = false
    )
    private Faculty faculty;

    @ManyToMany
    @JoinTable(
            name = "student_society",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "society_id")
    )
    private Set<Society> societies;

    @ManyToMany
    @JoinTable(
            name = "student_events",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new HashSet<>();

    // Getters and Setters
    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(Set<Society> societies) {
        this.societies = societies;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    // Constructors
    public Student() { }

    public Student(Set<Department> departments, User user, Faculty faculty) {
        setUser(user);
        setDepartments(departments);
        setFaculty(faculty);
    }
}
