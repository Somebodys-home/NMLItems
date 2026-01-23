package io.github.NoOne.nMLItems.enums;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE,
    JADE_FLOWER_BUNDLE;

    public static String getCropTypeString(CropType cropType) {
        switch (cropType) {
            case WHEAT_BUNDLE: return "wheat_bundle";
            case SUGAR_CANE: return "sugar_cane";
            case JADE_FLOWER_BUNDLE: return "jade_flower_bundle";
        }

        return "";
    }

    public static String getPlayerProfileString(CropType cropType) {
        switch (cropType) {
            case JADE_FLOWER_BUNDLE: return "JADE_FLOWERS";
        }

        return "";
    }
}
