package com.tracker.backend.models.enums;

public enum DayOfWeek {
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String value;

    DayOfWeek(String value) {
        this.value = value;
    }

    // Method to get the string value of the enum
    public String getValue() {
        return value;
    }

    // Static method to convert a SQL enum value to a Java enum
    public static DayOfWeek fromValue(String value) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.value.equalsIgnoreCase(value)) { // Using equalsIgnoreCase to make it case insensitive
                return day;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}