package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    @Column(name = "cover_image_url", length = 512)
    private String coverImageURL;

    // Seller: only one will be non-null
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = true)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "society_id", nullable = true)
    private Society society;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public Item(String name, String description, BigDecimal price, int stock, Faculty faculty, Department department, Society society, String coverImageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.faculty = faculty;
        this.department = department;
        this.society = society;
        this.coverImageURL = coverImageURL;
    }

    public Item() { }
}

