package com.tracker.backend.models;

import com.tracker.backend.models.enums.Category;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

public class DailyActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @Column(name = "name")
    private String name;
    @Column(name = "activity_description")
    private String activityDescription;

    @Column(name = "duration")
    private int duration;

    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    // Default constructor
    public DailyActivity() {
    }

    // Constructor with parameters
    public DailyActivity(int activityId, int userId, Category category, String name,
                         String activityDescription, int duration, Date date, Timestamp createdAt) {
        this.activityId = activityId;
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.activityDescription = activityDescription;
        this.duration = duration;
        this.date = date;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // toString method for easy representation
    @Override
    public String toString() {
        return "DailyActivity{" +
                "activityId=" + activityId +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                ", duration=" + duration +
                ", date=" + date +
                ", createdAt=" + createdAt +
                '}';
    }
}