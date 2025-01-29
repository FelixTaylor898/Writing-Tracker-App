package com.tracker.backend.controllers;

import com.tracker.backend.models.AppUserDTO;
import com.tracker.backend.models.CustomUserDetails;
import com.tracker.backend.security.JwtConverter;
import com.tracker.backend.security.AppUserService;
import com.tracker.backend.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService userService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtConverter converter;
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService userService, AuthenticationManager authenticationManager, JwtConverter converter, AppUserService appUserService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));
        if (appUserService.findByUsername(credentials.get("username")) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Authentication authentication = authenticationManager.authenticate(authToken);
        // Use CustomUserDetails instead of User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // Check if the authentication is successful
        if (authentication.isAuthenticated()) {
            // Generate JWT token
            String jwtToken = converter.getTokenFromUser(customUserDetails);
            // Prepare the response with the JWT token
            HashMap<String, String> map = new HashMap<>();
            map.put("jwt_token", jwtToken);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        // Return forbidden status if authentication fails
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AppUser updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // Find the authenticated user
        AppUser currentUser = appUserService.findByUsername(customUserDetails.getUsername());

        // Check if the user is trying to update their own account
        if (currentUser.getUserId().equals(updatedUser.getUserId()) || isAdmin()) {
            AppUser updatedAccount = appUserService.save(updatedUser);
            return new ResponseEntity<>(new AppUserDTO(updatedAccount), HttpStatus.OK);
        }

        // If neither condition is met, return forbidden
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");
        String email = credentials.get("email");

        AppUser appUser = new AppUser(username, password, email);
        appUser.setCreatedAt(new Date());
        appUser.setRole("USER");
        appUser = appUserService.save(appUser);

        HashMap<String, Integer> map = new HashMap<>();

        map.put("appUserId", appUser.getUserId().intValue());

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // Find the authenticated user
        AppUser currentUser = userService.findByUsername(customUserDetails.getUsername());

        if (currentUser.getUserId().equals(id) || isAdmin()) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // If neither condition is met, return forbidden
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // GET all users
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        List<AppUser> users = userService.findAll();
        // Map AppUser to AppUserDTO
        List<AppUserDTO> userDTOs = users.stream().map(AppUserDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable("id") String id) {
        Long uid = Long.parseLong(id);
        if (isAdmin()) {
            AppUser user = userService.findById(uid);
            if (user != null) {
                return new ResponseEntity<>(new AppUserDTO(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // GET user by email
    @GetMapping("/email")
    public ResponseEntity<AppUserDTO> getUserByEmail(@RequestParam("email") String email) {
        if (isAdmin()) {
            AppUser user = userService.findByEmail(email);
            if (user != null) {
                return new ResponseEntity<>(new AppUserDTO(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // Helper method to check if the current user has admin privileges
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    // Endpoint to make a user an admin
    @PutMapping("/{id}/make-admin")
    public ResponseEntity<String> makeUserAdmin(
            @PathVariable("id") Long userId,
            Authentication authentication) {

        try {
            boolean success = appUserService.makeAdmin(userId, authentication);
            if (success) {
                return new ResponseEntity<>("User promoted to admin successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (SecurityException e) {
            return new ResponseEntity<>("Only admins can perform this action", HttpStatus.FORBIDDEN);
        }
    }

    // Endpoint to revert an admin back to a user
    @PutMapping("/{id}/make-user")
    public ResponseEntity<String> makeAdminUser(
            @PathVariable("id") Long userId,
            Authentication authentication) {

        try {
            boolean success = appUserService.makeUser(userId, authentication);
            if (success) {
                return new ResponseEntity<>("Admin reverted to user successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (SecurityException e) {
            return new ResponseEntity<>("Only admins can perform this action", HttpStatus.FORBIDDEN);
        }
    }
}