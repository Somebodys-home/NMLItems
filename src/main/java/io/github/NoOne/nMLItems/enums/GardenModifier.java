package io.github.NoOne.nMLItems.enums;

public enum GardenModifier {
    FERTILIZER,
    WATERING_CAN;

    public static GardenModifier fromString(String string) {
        return switch (string) {
            case "fertilizer" -> FERTILIZER;
            case "watering_can" -> WATERING_CAN;
            default -> null;
        };
    }

    public static String toString(GardenModifier gardenModifier) {
        return switch (gardenModifier) {
            case FERTILIZER -> "fertilizer";
            case WATERING_CAN -> "watering_can";
        };
    }
}
