package io.github.NoOne.nMLItems.enums;

import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    MYTHICAL,
    RELIC;

    public static String toString(ItemRarity rarity) {
        return switch (rarity) {
            case COMMON -> "common";
            case UNCOMMON -> "uncommon";
            case RARE -> "rare";
            case MYTHICAL -> "mythical";
            case RELIC -> "relic";
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
   

    public static ChatColor toChatColor(ItemRarity rarity) {
        return switch (rarity) {
            case COMMON -> ChatColor.GRAY;
            case UNCOMMON -> ChatColor.GREEN;
            case RARE -> ChatColor.AQUA;
            case MYTHICAL -> ChatColor.LIGHT_PURPLE;
            case RELIC -> ChatColor.DARK_RED;
        };
    }
}
