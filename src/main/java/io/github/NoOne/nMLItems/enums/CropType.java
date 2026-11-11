package io.github.NoOne.nMLItems.enums;

public enum CropType {
    WHEAT_BUNDLE,
    SUGAR_CANE,
    JADE_FLOWER,
    RHUBARB;

    @Override
    public String toString() {
        return switch (this) {
            case WHEAT_BUNDLE -> "Wheat Bundle";
            case SUGAR_CANE ->  "Sugar Cane";
            case JADE_FLOWER -> "Jade Flower";
            case RHUBARB -> "Rhubarb";
        };
    }

    public SeedType toSeedType() {
        return switch (this) {
            case WHEAT_BUNDLE -> SeedType.WHEAT_SEEDS;
            case SUGAR_CANE -> SeedType.SUGAR_CANE;
            case JADE_FLOWER -> SeedType.JADE_SEEDS;
            case RHUBARB -> SeedType.RHUBARB_SEEDS;
        };
    }

    public String toColor(CropType cropType) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> "§6";
            case SUGAR_CANE -> "§a";
            case JADE_FLOWER -> "<SOLID:#00A86B>";
            case RHUBARB -> "<SOLID:#FC035A>";
        };
    }

    public static CropType fromString(String cropType) {
        return switch (cropType.toLowerCase().replace(" ", "_")) { // have to do this for its generate command
            case "wheat_bundle" -> WHEAT_BUNDLE;
            case "sugar_cane" -> SUGAR_CANE;
            case "jade_flower" -> JADE_FLOWER;
            case "rhubarb" -> RHUBARB;
            default -> null;
        };
    }
}
