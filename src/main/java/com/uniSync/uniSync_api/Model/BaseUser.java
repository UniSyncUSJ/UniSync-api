package com.uniSync.uniSync_api.Model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseUser {

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    @Column(unique = true, nullable = false)
    protected String email;

    @Column(nullable = false)
    protected String passwordHash;

    @Column
    protected LocalDateTime dob;

    @Column
    protected String Role;

    @Column
    protected String profilePicURL;

    @Column
    protected String phone;

    @Column
    protected LocalDateTime createdAt;

    @Column
    protected LocalDateTime lastUpdate;

    @Column
    protected LocalDateTime lastLogin;
}