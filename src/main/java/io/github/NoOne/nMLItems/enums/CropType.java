package io.github.NoOne.nMLItems.enums;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE,
    JADE_FLOWER,
    RHUBARB;

    public static String toString(CropType cropType) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> "wheat_bundle";
            case SUGAR_CANE -> "sugar_cane";
            case JADE_FLOWER -> "jade_flower";
            case RHUBARB -> "rhubarb";
        };
    }

    public static CropType fromString(String cropType) {
        return switch (cropType) {
            case "wheat_bundle" -> WHEAT_BUNDLE;
            case "sugar_cane" -> SUGAR_CANE;
            case "jade_flower" -> JADE_FLOWER;
            case "rhubarb" -> RHUBARB;
            default -> null;
        };
    }

    public static SeedType toSeedType(CropType cropType) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> SeedType.WHEAT_SEEDS;
            case SUGAR_CANE -> SeedType.SUGAR_CANE;
            case JADE_FLOWER -> SeedType.JADE_SEEDS;
            case RHUBARB -> SeedType.RHUBARB_SEEDS;
        };
    }

    public static String toColor(CropType cropType) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> "§6";
            case SUGAR_CANE -> "§a";
            case JADE_FLOWER -> "<SOLID:#00A86B>";
            case RHUBARB -> "<SOLID:#FC035A>";
        };
    }
}
