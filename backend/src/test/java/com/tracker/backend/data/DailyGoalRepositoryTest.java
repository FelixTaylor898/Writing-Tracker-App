package com.tracker.backend.data;

import com.tracker.backend.security.AppUserService;
import com.tracker.backend.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AppUserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testFindAll() {
        List<AppUser> mockUsers = new ArrayList<>();
        mockUsers.add(new AppUser(1L, "John Doe", "john@example.com"));
        mockUsers.add(new AppUser(2L, "Jane Doe", "jane@example.com"));

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<AppUser> users = userService.findAll();
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById_WhenUserExists() {
        AppUser mockUser = new AppUser(1L, "JohnDoe", "john@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        AppUser user = userService.findById(1L);
        assertNotNull(user);
        assertEquals("JohnDoe", user.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        AppUser user = userService.findById(1L);
        assertNull(user);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        AppUser newUser = new AppUser(0, "John Doe", "john@example.com");
        newUser.setPasswordHash("password123");
        AppUser savedUser = new AppUser(1L, "John Doe", "john@example.com");

        when(userRepository.save(newUser)).thenReturn(savedUser);

        AppUser result = userService.save(newUser);
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testDelete() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindByEmail_WhenUserExists() {
        AppUser mockUser = new AppUser(1L, "JohnDoe", "john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(mockUser));

        AppUser user = userService.findByEmail("john@example.com");
        assertNotNull(user);
        assertEquals("JohnDoe", user.getUsername());
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void testFindByEmail_WhenUserDoesNotExist() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        AppUser user = userService.findByEmail("john@example.com");
        assertNull(user);
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }
}