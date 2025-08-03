package io.github.NoOne.nMLItems;

import org.bukkit.ChatColor;

import java.util.Map;

public enum ItemStat {
    // damage stats
    PHYSICALDAMAGE,
    FIREDAMAGE,
    COLDDAMAGE,
    EARTHDAMAGE,
    LIGHTNINGDAMAGE,
    AIRDAMAGE,
    LIGHTDAMAGE,
    DARKDAMAGE,
    PUREDAMAGE,

    // defense stats
    EVASION,
    DEFENSE,
    OVERHEALTH,
    BLOCK,
    PHYSICALRESIST,
    FIRERESIST,
    COLDRESIST,
    EARTHRESIST,
    LIGHTNINGRESIST,
    AIRRESIST,
    LIGHTRESIST,
    DARKRESIST;

    public static String getStatString(ItemStat stat) {
        String statString;

        switch (stat) {
            case PHYSICALDAMAGE -> statString = "Physical Damage";
            case FIREDAMAGE -> statString = "Fire Damage";
            case COLDDAMAGE -> statString = "Cold Damage";
            case EARTHDAMAGE -> statString = "Earth Damage";
            case LIGHTNINGDAMAGE -> statString = "Lightning Damage";
            case AIRDAMAGE -> statString = "Air Damage";
            case LIGHTDAMAGE -> statString = "Light Damage";
            case DARKDAMAGE -> statString = "Dark Damage";
            case PUREDAMAGE -> statString = "Pure Damage";
            case EVASION -> statString = "Evasion";
            case DEFENSE -> statString = "Defense";
            case OVERHEALTH -> statString = "Overhealth";
            case BLOCK -> statString = "Block";
            case PHYSICALRESIST -> statString = "Physical Resist";
            case FIRERESIST -> statString = "Fire Resist";
            case COLDRESIST -> statString = "Cold Resist";
            case EARTHRESIST -> statString = "Earth Resist";
            case LIGHTNINGRESIST -> statString = "Lightning Resist";
            case AIRRESIST -> statString = "Air Resist";
            case LIGHTRESIST -> statString = "Light Resist";
            case DARKRESIST -> statString = "Dark Resist";
            default -> statString = "";
        }

        return statString;
    }

    public static ChatColor getStatColor(ItemStat stat) {
        ChatColor color;

        switch (stat) {
            case EVASION, LIGHTDAMAGE, PUREDAMAGE, LIGHTRESIST, BLOCK -> color = ChatColor.WHITE;
            case DEFENSE -> color = ChatColor.GREEN;
            case OVERHEALTH -> color = ChatColor.DARK_BLUE;
            case PHYSICALDAMAGE, PHYSICALRESIST -> color = ChatColor.DARK_RED;
            case FIREDAMAGE, FIRERESIST -> color = ChatColor.RED;
            case COLDDAMAGE, COLDRESIST -> color = ChatColor.AQUA;
            case EARTHDAMAGE, EARTHRESIST -> color = ChatColor.DARK_GREEN;
            case LIGHTNINGDAMAGE, LIGHTNINGRESIST -> color = ChatColor.YELLOW;
            case AIRDAMAGE, AIRRESIST -> color = ChatColor.GRAY;
            case DARKDAMAGE, DARKRESIST -> color = ChatColor.DARK_PURPLE;
            default -> color = null;
        }

        return color;
    }

    public static String getStatEmoji(ItemStat stat) {
        String statEmoji;

        switch (stat) {
            case PHYSICALDAMAGE, PHYSICALRESIST -> statEmoji = "⚔";
            case FIREDAMAGE, FIRERESIST -> statEmoji = "\uD83D\uDD25";
            case COLDDAMAGE, COLDRESIST -> statEmoji = "❄";
            case EARTHDAMAGE, EARTHRESIST -> statEmoji = "\uD83E\uDEA8";
            case LIGHTNINGDAMAGE, LIGHTNINGRESIST -> statEmoji = "\uD83D\uDDF2";
            case AIRDAMAGE, AIRRESIST -> statEmoji = "☁";
            case LIGHTDAMAGE, LIGHTRESIST -> statEmoji = "✦";
            case DARKDAMAGE, DARKRESIST -> statEmoji = "\uD83C\uDF00";
            case PUREDAMAGE -> statEmoji = "\uD83D\uDCA2";
            case BLOCK, DEFENSE -> statEmoji = "🛡️";
            case OVERHEALTH -> statEmoji = "\uD83D\uDC99";
            case EVASION -> statEmoji = "\uD83D\uDCA8";
            default -> statEmoji = "";
        }

        return statEmoji;
    }
}
