package io.github.NoOne.nMLItems.enums;

public enum ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    MYTHICAL,
    RELIC;

    @Override
    public String toString() {
        return switch (this) {
            case COMMON -> "common";
            case UNCOMMON -> "uncommon";
            case RARE -> "rare";
            case MYTHICAL -> "mythical";
            case RELIC -> "relic";
        };
    }

    public String toChatColor() {
        return switch (this) {
            case COMMON -> "§7";
            case UNCOMMON -> "§a";
            case RARE -> "§b";
            case MYTHICAL -> "§d";
            case RELIC -> "§4";
        };
    }

    public static ItemRarity fromString(String string) {
        return switch (string) {
            case "common" -> COMMON;
            case "uncommon" -> UNCOMMON;
            case "rare" -> RARE;
            case "mythical" -> MYTHICAL;
            case "relic" -> RELIC;
            default -> null;
        };
    }

    public double getRarityMultiplier() {
        return switch (this) {
            case COMMON -> .85;
            case RARE -> 1.25;
            case MYTHICAL -> 1.5;
            default -> 1;
        };
    }
}
