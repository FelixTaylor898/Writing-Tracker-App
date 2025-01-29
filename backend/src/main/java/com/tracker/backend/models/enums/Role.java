package com.tracker.backend.models.enums;

public enum Role {
    USER,
    ADMIN;

    // Convert Enum to String
    public String toString() {
        return name();  // This returns the name of the enum constant as a String
    }

    // Convert String to Enum
    public static Role fromString(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleString); // Handle invalid input
        }
    }
}