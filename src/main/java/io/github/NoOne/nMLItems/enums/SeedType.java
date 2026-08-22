package io.github.NoOne.nMLItems.enums;

public enum SeedType {
    WHEAT_SEEDS,
    SUGAR_CANE,
    JADE_SEEDS,
    RHUBARB_SEEDS;

    public static String toString(SeedType seedType) {
        return switch (seedType) {
            case WHEAT_SEEDS -> "wheat_seeds";
            case SUGAR_CANE -> "sugar_cane";
            case JADE_SEEDS -> "jade_seeds";
            case RHUBARB_SEEDS -> "rhubarb_seeds";
        };
    }

    public static SeedType fromString(String string) {
        return switch (string) {
            case "wheat_seeds" -> WHEAT_SEEDS;
            case "sugar_cane" -> SUGAR_CANE;
            case "jade_seeds" -> JADE_SEEDS;
            case "rhubarb_seeds" -> RHUBARB_SEEDS;
            default -> null;
        };
    }

    public static CropType toCropType(SeedType seedType) {
        return switch (seedType) {
            case WHEAT_SEEDS -> CropType.WHEAT_BUNDLE;
            case SUGAR_CANE -> CropType.SUGAR_CANE;
            case JADE_SEEDS -> CropType.JADE_FLOWER;
            case RHUBARB_SEEDS -> CropType.RHUBARB;
        };
    }
}
