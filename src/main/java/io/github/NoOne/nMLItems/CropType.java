package io.github.NoOne.nMLItems;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE;

    public static String getCropTypeString(CropType cropType) {
        switch (cropType) {
            case WHEAT_BUNDLE: return "wheat_bundle";
            case SUGAR_CANE: return "sugar_cane";
        }

        return "";
    }
}
