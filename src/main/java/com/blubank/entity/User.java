package com.blubank.entity;

import javax.persistence.*;

@Entity
@Table (name = "Clients")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;
    private String phone;
    private String age;
    private String authKey;

    public User() {
    }

    public User(String name, String surname, String password, UserRole role, String email, String phone, String age) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
