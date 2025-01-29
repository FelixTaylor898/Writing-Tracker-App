package com.tracker.backend.domain;

import com.tracker.backend.data.DailyGoalRepository;
import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import com.tracker.backend.models.enums.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyGoalService {

    private final DailyGoalRepository dailyGoalRepository;

    @Autowired
    public DailyGoalService(DailyGoalRepository dailyGoalRepository) {
        this.dailyGoalRepository = dailyGoalRepository;
    }

    // Create a new daily goal
    public DailyGoal createDailyGoal(DailyGoal dailyGoal) {
        return dailyGoalRepository.save(dailyGoal);
    }

    // Update an existing daily goal
    public DailyGoal updateDailyGoal(Long goalId, DailyGoal updatedGoal) {
        Optional<DailyGoal> optionalGoal = dailyGoalRepository.findById(goalId);
        if (optionalGoal.isPresent()) {
            DailyGoal existingGoal = optionalGoal.get();
            existingGoal.setCategory(updatedGoal.getCategory());
            existingGoal.setGoalValue(updatedGoal.getGoalValue());
            existingGoal.setDayOfWeek(updatedGoal.getDayOfWeek());
            existingGoal.setGoalTime(updatedGoal.getGoalTime());
            existingGoal.setStatus(updatedGoal.getStatus());
            return dailyGoalRepository.save(existingGoal);
        }
        return null;
    }

    // Delete a daily goal by ID
    public void deleteDailyGoal(Long goalId) {
        dailyGoalRepository.deleteById(goalId);
    }

    // Find DailyGoals by category
    public List<DailyGoal> getGoalsByCategory(Category category) {
        return dailyGoalRepository.findByCategory(category);
    }

    // Find DailyGoals by user ID
    public List<DailyGoal> getGoalsByUserId(Long userId) {
        return dailyGoalRepository.findByUserId(userId);
    }

    // Find active DailyGoals
    public List<DailyGoal> getActiveGoals() {
        return dailyGoalRepository.findByStatus(true);
    }

    // Find DailyGoals by day of the week
    public List<DailyGoal> getGoalsByDayOfWeek(DayOfWeek dayOfWeek) {
        return dailyGoalRepository.findByDayOfWeek(dayOfWeek);
    }
}