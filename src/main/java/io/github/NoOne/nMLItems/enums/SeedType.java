package io.github.NoOne.nMLItems.enums;

public enum SeedType {
    WHEAT_SEEDS,
    SUGAR_CANE,
    JADE_SEEDS,
    RHUBARB_SEEDS;

    public static String toString(SeedType seedType) {
        return switch (seedType) {
            case WHEAT_SEEDS -> "Wheat Seeds";
            case SUGAR_CANE -> "Sugar Cane";
            case JADE_SEEDS -> "Jade Seeds";
            case RHUBARB_SEEDS -> "Rhubarb Seeds";
        };
    }

    public static SeedType fromString(String string) {
        return switch (string.toLowerCase().replace(" ", "_")) { // have to do this for its generate command
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
