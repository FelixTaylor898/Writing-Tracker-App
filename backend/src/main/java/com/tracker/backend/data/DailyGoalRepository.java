package com.tracker.backend.data;

import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import com.tracker.backend.models.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyGoalRepository extends JpaRepository<DailyGoal, Integer> {

    // Find DailyGoals by category
    List<DailyGoal> findByCategory(Category category);

    // Find DailyGoals by user ID
    List<DailyGoal> findByUserId(Integer userId);

    // Find DailyGoals by active status
    List<DailyGoal> findByStatus(Boolean status);

    // Find DailyGoals by day of the week
    List<DailyGoal> findByDayOfWeek(DayOfWeek dayOfWeek);
}