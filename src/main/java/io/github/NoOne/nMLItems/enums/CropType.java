package io.github.NoOne.nMLItems.enums;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE,
    JADE_FLOWER,
    RHUBARB;

    public static String getCropTypeString(CropType cropType) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> "wheat_bundle";
            case SUGAR_CANE -> "sugar_cane";
            case JADE_FLOWER -> "jade_flower";
            case RHUBARB -> "rhubarb";
        };

    }

    public static CropType getCropType(String cropType) {
        return switch (cropType) {
            case "wheat_bundle" -> WHEAT_BUNDLE;
            case "sugar_cane" -> SUGAR_CANE;
            case "jade_flower" -> JADE_FLOWER;
            case "rhubarb" -> RHUBARB;
            default -> null;
        };

    }
}
