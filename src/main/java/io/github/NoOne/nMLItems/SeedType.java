package io.github.NoOne.nMLItems;

public enum SeedType {
    WHEAT_SEEDS,
    SUGAR_CANE,
    JADE_SEEDS;

    public static String getSeedTypeString(SeedType seedType) {
        switch (seedType) {
            case WHEAT_SEEDS: return "wheat_seeds";
            case SUGAR_CANE: return "sugar_cane";
            case JADE_SEEDS: return "jade_seeds";
        }

        return "";
    }

    public static SeedType getSeedType(String string) {
        switch (string) {
            case "wheat_seeds": return WHEAT_SEEDS;
            case "sugar_cane": return SUGAR_CANE;
            case "jade_seeds": return JADE_SEEDS;
        }

        return null;
    }
}
