package com.tracker.backend.models;

import com.tracker.backend.models.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author Felix Taylor
 * @version 10/01/2024
 *
 * AppUser model class
 */
@Entity
@Table(name = "User")
public class AppUser {

    @Column(nullable = false, name = "role")
    private String role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long userId; // Corresponds to user_id in the database

    @Column(nullable = false, unique = true, length = 50)
    private String username; // Corresponds to username in the database

    @Column(nullable = false, unique = true, length = 100)
    private String email; // Corresponds to email in the database

    @Column(nullable = false)
    private String passwordHash; // Corresponds to password_hash in the database

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Corresponds to created_at in the database

    public AppUser(long l, String name, String email) {
        this.userId = l;
        this.username = name;
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AppUser() {

    }

    public AppUser(String username, String password, String email) {
        this.username = username;
        this.passwordHash = password;
        this.email = email;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    public boolean isAdmin() {
        return role.equals("ADMIN");
    }
}