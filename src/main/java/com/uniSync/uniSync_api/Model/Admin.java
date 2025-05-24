package com.uniSync.uniSync_api.Model;

import com.uniSync.uniSync_api.Common.AdminType;
import jakarta.persistence.*;

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


    // Constructors
    public Admin(){ }

    public Admin(User user, AdminType adminType) {
        setUser(user);
        setAdminType(adminType);
    }
}
