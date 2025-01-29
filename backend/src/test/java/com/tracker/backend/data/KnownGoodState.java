package com.tracker.backend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class KnownGoodState {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public KnownGoodState(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void reset() {
        try {
            // Call the stored procedure to reset the database state
            jdbcTemplate.execute("CALL reset_database_state();");
            System.out.println("Database reset successfully.");
        } catch (Exception e) {
            // Handle the exception if there's any issue in calling the procedure
            System.err.println("Error while resetting the database: " + e.getMessage());
        }
    }
}
