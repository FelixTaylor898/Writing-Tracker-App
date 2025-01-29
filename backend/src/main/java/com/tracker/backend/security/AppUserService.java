package com.tracker.backend.security;

import com.tracker.backend.data.AppUserRepository;
import com.tracker.backend.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean isUniqueEmail(String email, Long userId) {
        // Retrieve all users
        List<AppUser> allUsers = userRepository.findAll();

        // Check if the username or email is already taken by another user
        for (AppUser existingUser : allUsers) {
            if (!existingUser.getUserId().equals(userId)) { // Skip the current user if updating
                if (existingUser.getEmail().equals(email)) {
                    return false; // Not unique
                }
            }
        }
        return true;
    }

    public boolean isUniqueUsername(String username, Long userId) {
        // Retrieve all users
        List<AppUser> allUsers = userRepository.findAll();

        // Check if the username or email is already taken by another user
        for (AppUser existingUser : allUsers) {
            if (!existingUser.getUserId().equals(userId)) { // Skip the current user if updating
                if (existingUser.getUsername().equals(username)) {
                    return false; // Not unique
                }
            }
        }
        return true;
    }

    public AppUser save(AppUser user) {
        // Check if user is updating or creating a new user
        // Existing user, check for uniqueness excluding this user
        AppUser existingUser = null;
        if (user.getUserId() != null) {
            existingUser = userRepository.findById(user.getUserId()).orElse(null);
        }

        // Check if username or email have changed
        if (existingUser != null) {
            boolean emailExists = isUniqueEmail(user.getEmail(), user.getUserId());
            boolean usernameExists = isUniqueUsername(user.getUsername(), user.getUserId());

            if (usernameExists) {
                throw new DataIntegrityViolationException("Username already taken.");
            }
            if (emailExists) {
                throw new DataIntegrityViolationException("Email already in use.");
            }

            user.setRole(existingUser.getRole());
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        // Save the user (will perform either INSERT or UPDATE)
        return userRepository.save(user);
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public AppUser findByUsername(String authenticatedUsername) {
        return userRepository.findByUsername(authenticatedUsername).orElse(null);
    }

    private boolean isAdmin(Authentication authentication) {
        AppUser loggedInUser = (AppUser) authentication.getPrincipal();
        return loggedInUser.getRole().equals("ADMIN");
    }

    // Method to turn a user into an admin
    public boolean makeAdmin(Long userId, Authentication authentication) {
        // Check if the logged-in user is an admin
        if (!isAdmin(authentication)) {
            throw new SecurityException("Only admins can perform this action.");
        }

        // Fetch the user whose role we want to change
        Optional<AppUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser appUser = optionalUser.get();
            appUser.setRole("ADMIN");
            userRepository.save(appUser); // Save the updated role
            return true;
        }
        return false; // User not found
    }

    // Method to turn an admin back into a user
    public boolean makeUser(Long userId, Authentication authentication) {
        // Check if the logged-in user is an admin
        if (!isAdmin(authentication)) {
            throw new SecurityException("Only admins can perform this action.");
        }

        // Fetch the user whose role we want to change
        Optional<AppUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser appUser = optionalUser.get();
            appUser.setRole("USER");
            userRepository.save(appUser); // Save the updated role
            return true;
        }
        return false; // User not found
    }
}