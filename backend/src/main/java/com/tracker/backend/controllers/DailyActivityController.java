package com.tracker.backend.controllers;

import com.tracker.backend.domain.DailyActivityService;
import com.tracker.backend.models.DailyActivity;
import com.tracker.backend.models.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily-activity")
public class DailyActivityController {

    private final DailyActivityService dailyActivityService;

    @Autowired
    public DailyActivityController(DailyActivityService dailyActivityService) {
        this.dailyActivityService = dailyActivityService;
    }

    // Create a new daily activity
    @PostMapping
    public ResponseEntity<DailyActivity> createDailyActivity(@RequestBody DailyActivity dailyActivity) {
        DailyActivity createdActivity = dailyActivityService.addDailyActivity(dailyActivity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<DailyActivity> updateDailyActivity(
            @PathVariable Integer activityId,
            @RequestBody DailyActivity updatedActivity) {
        DailyActivity Activity = dailyActivityService.updateDailyActivity(activityId, updatedActivity);
        if (Activity != null) {
            return new ResponseEntity<>(Activity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a daily activity by ID
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteDailyActivity(@PathVariable Integer dailyActivity) {
        dailyActivityService.deleteActivity(dailyActivity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get DailyActivity by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<DailyActivity>> getActivitysByCategory(@PathVariable Category category) {
        List<DailyActivity> activities = dailyActivityService.getActivityByCategory(category);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    // Get DailyActivitys by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DailyActivity>> getActivitysByUserId(@PathVariable Integer userId) {
        List<DailyActivity> Activitys = dailyActivityService.getActivityByUserId(userId);
        return new ResponseEntity<>(Activitys, HttpStatus.OK);
    }
}
