package com.tracker.backend.data;

import com.tracker.backend.models.DailyActivity;
import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyActivityRepository extends JpaRepository<DailyActivity, Integer> {

    // Find DailyGoals by user ID
    List<DailyActivity> findByUserId(Integer userId);

    List<DailyGoal> findByCategory(Category category);
}
