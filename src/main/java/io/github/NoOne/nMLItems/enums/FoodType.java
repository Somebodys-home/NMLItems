package io.github.NoOne.nMLItems.enums;

public enum FoodType {
    RHUBARB_PIE;

    public static String toString(FoodType foodType) {
        return switch (foodType) {
            case RHUBARB_PIE -> "rhubarb_pie";
        };
    }

    public static FoodType fromString(String foodType) {
        return switch (foodType) {
            case "rhubarb_pie" -> RHUBARB_PIE;
            default -> null;
        };
    }

    public static int getServings(FoodType foodType) {
        return switch (foodType) {
            case RHUBARB_PIE -> 3;
        };
    }
}
