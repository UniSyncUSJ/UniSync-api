package com.uniSync.uniSync_api.Model;

import com.uniSync.uniSync_api.Common.UserRole;
import com.uniSync.uniSync_api.utils.HashUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private LocalDate birthDate;
    private UserRole role;
    private String profilePicURL;
    private String phoneNumber;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdate;
    private LocalDateTime lastLogin;

    //Constructors
    public User() { }

    public User(String firstName, String lastName, String email, String password, LocalDate birthDate, UserRole role, String profilePicURL, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPasswordHash(password);
        setBirthDate(birthDate);
        setRole(role);
        setProfilePicURL(profilePicURL);
        setPhoneNumber(phoneNumber);
        this.createdOn = LocalDateTime.now();
        setLastUpdate();
        setLastLogin();
    }

    public User(String firstName, String lastName, String email, String password, UserRole role, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPasswordHash(password);
        setRole(role);
        setProfilePicURL(null); //default profile pic url;
        setPhoneNumber(phoneNumber);
        this.createdOn = LocalDateTime.now();
        setLastUpdate();
        setLastLogin();
    }

    //Getters & Setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = HashUtil.hashPassword(password);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setLastUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
}
