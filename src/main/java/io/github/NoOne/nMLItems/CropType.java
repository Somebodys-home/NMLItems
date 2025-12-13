package io.github.NoOne.nMLItems;

public enum CropType {
    WHEAT_BUNDLE;

    public static String getCropTypeString(CropType cropType) {
        switch (cropType) {
            case WHEAT_BUNDLE: return "wheat_bundle";
        }

        return "";
    }

    public static CropType getCropType(String string) {
        switch (string) {
            case "wheat_bundle": return WHEAT_BUNDLE;
        }

        return null;
    }
}
