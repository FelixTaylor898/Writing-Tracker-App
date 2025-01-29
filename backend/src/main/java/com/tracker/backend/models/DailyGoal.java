package com.tracker.backend.models;

import com.tracker.backend.models.enums.Category;
import com.tracker.backend.models.enums.DayOfWeek;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "DailyGoal")
public class DailyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "goal_value")
    private Integer goalValue; // Goal value in minutes

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // Day of the week

    @Column(name = "goal_time")
    private String goalTime; // Optional time of day to complete the goal

    @Column(nullable = false)
    private Boolean status = true; // Tracks if the goal is active (1) or inactive (0)

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    public DailyGoal() {
        // Default constructor
    }

    public DailyGoal(Long userId, Category category, Integer goalValue, DayOfWeek dayOfWeek, String goalTime) {
        this.userId = userId;
        this.category = category;
        this.goalValue = goalValue;
        this.dayOfWeek = dayOfWeek;
        this.goalTime = goalTime;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getGoalValue() {
        return goalValue;
    }

    public void setGoalValue(Integer goalValue) {
        this.goalValue = goalValue;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}