package com.tracker.backend.models.enums;

public enum Size {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big");

    private final String value;

    Size(String value) {
        this.value = value;
    }

    // Method to get the string value of the enum
    public String getValue() {
        return value;
    }

    // Static method to convert a SQL enum value to a Java enum
    public static Size fromValue(String value) {
        for (Size rewardSize : Size.values()) {
            if (rewardSize.value.equalsIgnoreCase(value)) { // Using equalsIgnoreCase for case-insensitive comparison
                return rewardSize;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}