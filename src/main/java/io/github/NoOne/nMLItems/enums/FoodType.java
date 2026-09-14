package io.github.NoOne.nMLItems.enums;

public enum FoodType {
    RHUBARB_PIE;

    public static String toString(FoodType foodType) {
        return switch (foodType) {
            case RHUBARB_PIE -> "Rhubarb Pie";
        };
    }

    public static FoodType fromString(String foodType) {
        return switch (foodType.toLowerCase().replace(" ", "_")) { // have to do this for its generate command
            case "rhubarb_pie" -> RHUBARB_PIE;
            default -> null;
        };
    }

    public static int getServings(FoodType foodType) {
        return switch (foodType) {
            case RHUBARB_PIE -> 3;
        };
    }

    public static String toColor(FoodType foodType) {
        return switch (foodType) {
            case RHUBARB_PIE -> "<SOLID:#FC035A>";
        };
    }
}
