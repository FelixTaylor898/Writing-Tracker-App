package com.tracker.backend.domain;

import com.tracker.backend.data.DailyActivityRepository;
import com.tracker.backend.models.DailyActivity;
import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyActivityService {

    private final DailyActivityRepository dailyActivityRepository;

    @Autowired
    public DailyActivityService(DailyActivityRepository dailyActivityRepository) {
        this.dailyActivityRepository = dailyActivityRepository;
    }

    public DailyActivity addDailyActivity(DailyActivity dailyActivity) {
        return dailyActivityRepository.save(dailyActivity);
    }

    // Update an existing daily activity
    public DailyActivity updateDailyGoal(Integer activityId, DailyActivity updatedActivity) {
        Optional<DailyActivity> optionalActivity = dailyActivityRepository.findById(activityId);
        if (optionalActivity.isPresent()) {
            DailyActivity existingActivity = optionalActivity.get();
            existingActivity.setActivityDescription(updatedActivity.getActivityDescription());
            existingActivity.setCategory(updatedActivity.getCategory());
            existingActivity.setCreatedAt(updatedActivity.getCreatedAt());
            existingActivity.setDate(updatedActivity.getDate());
            existingActivity.setName(updatedActivity.getName());
            existingActivity.setDuration(updatedActivity.getDuration());
            return dailyActivityRepository.save(existingActivity);
        }
        return null; // Or throw an exception
    }

    public void deleteActivity(Integer activityId) {
        dailyActivityRepository.deleteById(activityId);
    }

    public List<DailyGoal> getActivityByCategory(Category category) {
        return dailyActivityRepository.findByCategory(category);
    }

    public List<DailyActivity> getActivityByUserId(Integer userId) {
        return dailyActivityRepository.findByUserId(userId);
    }

}
