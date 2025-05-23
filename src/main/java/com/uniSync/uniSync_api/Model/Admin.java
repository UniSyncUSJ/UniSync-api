package com.uniSync.uniSync_api.Model;

import com.uniSync.uniSync_api.Common.AdminType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private AdminType adminType;

    private LocalDateTime createdOn;

    private LocalDateTime lastAction;

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AdminType getAdminType() {
        return adminType;
    }

    public void setAdminType(AdminType adminType) {
        this.adminType = adminType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn() {
        this.createdOn = user.getCreatedAt();
    }

    public LocalDateTime getLastAction() {
        return lastAction;
    }

    public void setLastAction() {
        this.lastAction = user.getLastUpdate();
    }

    // Constructors
    public Admin(User user, AdminType adminType) {
        setUser(user);
        setAdminType(adminType);
        setCreatedOn();
        setLastAction();
    }

    public Admin(){ }
}
