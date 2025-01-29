package com.tracker.backend.models.enums;

public enum Category {
    PRODUCT("product"),
    PROCESS("process"),
    SELF_CARE("self-care");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    // Method to get the string value of the enum
    public String getValue() {
        return value;
    }

    // Static method to convert a SQL enum value to a Java enum
    public static Category fromValue(String value) {
        for (Category category : Category.values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
