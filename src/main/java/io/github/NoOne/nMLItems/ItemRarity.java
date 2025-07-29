package io.github.NoOne.nMLItems;

import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    MYTHICAL,
    RELIC;

    public static String getItemRarityString(ItemRarity rarity) {
        switch (rarity) {
            case COMMON -> { return "common"; }
            case UNCOMMON -> { return "uncommon"; }
            case RARE -> { return "rare"; }
            case MYTHICAL -> { return "mythical"; }
            case RELIC -> { return "relic"; }
            default -> { return ""; }
        }
    }

    public static ItemRarity getItemRarityFromString(String string) {
        switch (string) {
            case "common" -> { return COMMON; }
            case "uncommon" -> { return UNCOMMON; }
            case "rare" -> { return RARE; }
            case "mythical" -> { return MYTHICAL; }
            case "relic" -> { return RELIC; }
            default -> { return null; }
        }
    }

    public static ChatColor getItemRarityColor(ItemRarity rarity) {
        switch (rarity) {
            case COMMON -> { return ChatColor.GRAY; }
            case UNCOMMON -> { return ChatColor.GREEN; }
            case RARE -> { return ChatColor.AQUA; }
            case MYTHICAL -> { return ChatColor.LIGHT_PURPLE; }
            case RELIC -> { return ChatColor.DARK_RED; }
            default -> { return null; }
        }
    }
}
