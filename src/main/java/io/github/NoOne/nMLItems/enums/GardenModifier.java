package io.github.NoOne.nMLItems.enums;

public enum GardenModifier {
    FERTILIZER,
    WATERING_CAN;

    @Override
    public String toString() {
        return switch (this) {
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
