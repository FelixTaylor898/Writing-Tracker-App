package com.tracker.backend.data;

import com.tracker.backend.BackendApplication;
import com.tracker.backend.models.DailyGoal;
import com.tracker.backend.models.enums.Category;
import com.tracker.backend.models.enums.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BackendApplication.class)
class DailyGoalRepositoryTest {

    @Autowired
    private DailyGoalRepository dailyGoalRepository;

    @Autowired
    private KnownGoodState state;

    @BeforeEach
    public void resetDatabase() {
        state.reset();
    }


    @Test
    public void testFindAll() {
        List<DailyGoal> goals = dailyGoalRepository.findAll();
        assertEquals(5, goals.size(), "There should be 5 goals");
    }
}