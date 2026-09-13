package io.github.NoOne.nMLItems.enums;

public enum GardenModifier {
    FERTILIZER,
    WATERING_CAN;

    public static String toString(GardenModifier gardenModifier) {
        return switch (gardenModifier) {
            case FERTILIZER -> "Fertilizer";
            case WATERING_CAN -> "Watering Can";
        };
    }

    public static GardenModifier fromString(String string) {
        return switch (string) {
            case "fertilizer" -> FERTILIZER;
            case "watering_can" -> WATERING_CAN;
            default -> null;
        };
    }
}
