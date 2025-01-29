package com.tracker.backend.controllers;

import com.tracker.backend.domain.DailyGoalService;
import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import com.tracker.backend.models.enums.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily-goals")
public class DailyGoalController {

    private final DailyGoalService dailyGoalService;

    @Autowired
    public DailyGoalController(DailyGoalService dailyGoalService) {
        this.dailyGoalService = dailyGoalService;
    }

    // Create a new daily goal
    @PostMapping
    public ResponseEntity<DailyGoal> createDailyGoal(@RequestBody DailyGoal dailyGoal) {
        DailyGoal createdGoal = dailyGoalService.createDailyGoal(dailyGoal);
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
    }

    // Update an existing daily goal
    @PutMapping("/{goalId}")
    public ResponseEntity<DailyGoal> updateDailyGoal(
            @PathVariable Long goalId,
            @RequestBody DailyGoal updatedGoal) {
        DailyGoal goal = dailyGoalService.updateDailyGoal(goalId, updatedGoal);
        if (goal != null) {
            return new ResponseEntity<>(goal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a daily goal by ID
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteDailyGoal(@PathVariable Long goalId) {
        dailyGoalService.deleteDailyGoal(goalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get DailyGoals by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<DailyGoal>> getGoalsByCategory(@PathVariable Category category) {
        List<DailyGoal> goals = dailyGoalService.getGoalsByCategory(category);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    // Get DailyGoals by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DailyGoal>> getGoalsByUserId(@PathVariable Long userId) {
        List<DailyGoal> goals = dailyGoalService.getGoalsByUserId(userId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    // Get all active DailyGoals
    @GetMapping("/active")
    public ResponseEntity<List<DailyGoal>> getActiveGoals() {
        List<DailyGoal> goals = dailyGoalService.getActiveGoals();
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    // Get DailyGoals by day of the week
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<DailyGoal>> getGoalsByDayOfWeek(@PathVariable DayOfWeek dayOfWeek) {
        List<DailyGoal> goals = dailyGoalService.getGoalsByDayOfWeek(dayOfWeek);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }
}