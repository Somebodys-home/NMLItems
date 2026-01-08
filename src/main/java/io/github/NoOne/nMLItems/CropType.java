package io.github.NoOne.nMLItems;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE,
    JADE_FLOWER_BUSH;

    public static String getCropTypeString(CropType cropType) {
        switch (cropType) {
            case WHEAT_BUNDLE: return "wheat_bundle";
            case SUGAR_CANE: return "sugar_cane";
            case JADE_FLOWER_BUSH: return "jade_flower_bush";
        }

        return "";
    }
}
