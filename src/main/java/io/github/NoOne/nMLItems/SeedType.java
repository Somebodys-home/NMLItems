package io.github.NoOne.nMLItems;

public enum SeedType {
    WHEAT_SEEDS,
    SUGAR_CANE;

    public static String getSeedTypeString(SeedType seedType) {
        switch (seedType) {
            case WHEAT_SEEDS: return "wheat_seeds";
            case SUGAR_CANE: return "sugar_cane";
        }

        return "";
    }

    public static SeedType getSeedType(String string) {
        switch (string) {
            case "wheat_seeds": return WHEAT_SEEDS;
            case "sugar_cane": return SUGAR_CANE;
        }

        return null;
    }
}
