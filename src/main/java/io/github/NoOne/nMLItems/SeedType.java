package io.github.NoOne.nMLItems;

public enum SeedType {
    WHEAT_SEEDS;

    public static String getSeedTypeString(SeedType seedType) {
        switch (seedType) {
            case WHEAT_SEEDS: return "wheat_seeds";
        }

        return "";
    }

    public static SeedType getSeedType(String string) {
        switch (string) {
            case "wheat_seeds": return WHEAT_SEEDS;
        }

        return null;
    }
}
