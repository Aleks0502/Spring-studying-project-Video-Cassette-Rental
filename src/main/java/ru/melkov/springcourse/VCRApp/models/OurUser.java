package ru.melkov.springcourse.VCRApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "app_user")
public class OurUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 3, max = 15, message = "Username should be between 3 and 15 characters!")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password cannot be empty!")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public OurUser() {

    }

    public OurUser(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "OurUser{" +
                "id=" + id +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
