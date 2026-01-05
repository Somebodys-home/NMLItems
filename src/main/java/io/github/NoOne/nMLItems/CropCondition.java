package io.github.NoOne.nMLItems;

public enum CropCondition {
    WATERED;

    public static String getCropConditionString(CropCondition cropCondition) {
        switch (cropCondition) {
            case WATERED: return "watered";
        }

        return "";
    }
}
