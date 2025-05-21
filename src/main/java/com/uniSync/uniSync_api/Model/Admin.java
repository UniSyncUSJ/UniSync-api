package com.uniSync.uniSync_api.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    private String role = "ADMIN";

    @Column
    private String adminType;
}
