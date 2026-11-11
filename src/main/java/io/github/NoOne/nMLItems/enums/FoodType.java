package io.github.NoOne.nMLItems.enums;

public enum FoodType {
    RHUBARB_PIE;

    @Override
    public String toString() {
        return switch (this) {
            case RHUBARB_PIE -> "Rhubarb Pie";
        };
    }

    public String toColor() {
        return switch (this) {
            case RHUBARB_PIE -> "<SOLID:#FC035A>";
        };
    }

    public int getServings() {
        return switch (this) {
            case RHUBARB_PIE -> 3;
        };
    }

    public static FoodType fromString(String foodType) {
        return switch (foodType.toLowerCase().replace(" ", "_")) { // have to do this for its generate command
            case "rhubarb_pie" -> RHUBARB_PIE;
            default -> null;
        };
    }
}
