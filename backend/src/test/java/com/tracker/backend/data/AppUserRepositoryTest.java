package com.tracker.backend.data;

import com.tracker.backend.BackendApplication;
import com.tracker.backend.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    KnownGoodState state;

    @BeforeEach
    public void resetDatabase() {
        state.reset();
    }

    @Test
    public void testFindByEmail() {
        // Test user with email 'testuser1@example.com' exists in the reset data
        Optional<AppUser> userOpt = userRepository.findByEmail("testuser1@example.com");

        assertTrue(userOpt.isPresent(), "User should exist");
        assertEquals("testuser1", userOpt.get().getUsername());
    }

    @Test
    public void testFindByUserId() {
        // Test that user with ID 1 exists in the reset data
        Optional<AppUser> userOpt = userRepository.findById(1L);

        assertTrue(userOpt.isPresent(), "User should exist");
        assertEquals("testuser1", userOpt.get().getUsername());
        assertEquals("testuser1@example.com", userOpt.get().getEmail());
    }

    @Test
    public void testSaveNewUser() {
        // Create a new user and save it
        AppUser newUser = new AppUser();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setPasswordHash("hashed_password");
        newUser.setCreatedAt(new Date());
        newUser.setRole("USER");

        AppUser savedUser = userRepository.save(newUser);

        // Check that the user was saved
        assertNotNull(savedUser.getUserId(), "User ID should not be null");
        assertEquals("newuser", savedUser.getUsername());

        // Check that we can retrieve the saved user from the database
        Optional<AppUser> foundUser = userRepository.findByEmail("newuser@example.com");
        assertTrue(foundUser.isPresent(), "Saved user should be retrievable by email");
    }

    @Test
    public void testDeleteUser() {
        // Fetch an existing user and delete it
        Optional<AppUser> userOpt = userRepository.findById(1L);
        assertTrue(userOpt.isPresent(), "User should exist");

        userRepository.deleteById(1L);

        // Ensure the user is deleted
        Optional<AppUser> deletedUser = userRepository.findById(1L);
        assertFalse(deletedUser.isPresent(), "User should be deleted");
    }

    @Test
    public void testFindAll() {
        // Fetch all users and verify that the reset procedure inserted three users
        List<AppUser> users = userRepository.findAll();
        assertEquals(3, users.size(), "There should be 3 users in the reset database");
    }
}