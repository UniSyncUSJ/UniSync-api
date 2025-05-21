package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
