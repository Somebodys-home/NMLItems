package io.github.NoOne.nMLItems;

import org.bukkit.ChatColor;

public enum ItemStat {
    PHYSICALDAMAGE,
    FIREDAMAGE,
    COLDDAMAGE,
    EARTHDAMAGE,
    LIGHTNINGDAMAGE,
    AIRDAMAGE,
    RADIANTDAMAGE,
    NECROTICDAMAGE,
    PUREDAMAGE,

    // defense stats
    EVASION,
    DEFENSE,
    OVERHEALTH,
    GUARD,
    PHYSICALRESIST,
    FIRERESIST,
    COLDRESIST,
    EARTHRESIST,
    LIGHTNINGRESIST,
    AIRRESIST,
    RADIANTRESIST,
    NECROTICRESIST,

    CRITCHANCE,
    CRITDAMAGE;

    public static String getStatString(ItemStat stat) {
        String statString;

        switch (stat) {
            case PHYSICALDAMAGE -> statString = "Physical Damage";
            case FIREDAMAGE -> statString = "Fire Damage";
            case COLDDAMAGE -> statString = "Cold Damage";
            case EARTHDAMAGE -> statString = "Earth Damage";
            case LIGHTNINGDAMAGE -> statString = "Lightning Damage";
            case AIRDAMAGE -> statString = "Air Damage";
            case RADIANTDAMAGE -> statString = "Radiant Damage";
            case NECROTICDAMAGE -> statString = "Necrotic Damage";
            case PUREDAMAGE -> statString = "Pure Damage";
            case EVASION -> statString = "Evasion";
            case DEFENSE -> statString = "Defense";
            case OVERHEALTH -> statString = "Overhealth";
            case GUARD -> statString = "Guard";
            case PHYSICALRESIST -> statString = "Physical Resist";
            case FIRERESIST -> statString = "Fire Resist";
            case COLDRESIST -> statString = "Cold Resist";
            case EARTHRESIST -> statString = "Earth Resist";
            case LIGHTNINGRESIST -> statString = "Lightning Resist";
            case AIRRESIST -> statString = "Air Resist";
            case RADIANTRESIST -> statString = "Radiant Resist";
            case NECROTICRESIST -> statString = "Necrotic Resist";
            case CRITCHANCE -> statString = "Crit Chance";
            case CRITDAMAGE -> statString = "Crit Damage";
            default -> statString = "";
        }

        return statString;
    }

    public static ChatColor getStatColor(ItemStat stat) {
        ChatColor color;

        switch (stat) {
            case EVASION, RADIANTDAMAGE, PUREDAMAGE, RADIANTRESIST, GUARD -> color = ChatColor.WHITE;
            case DEFENSE -> color = ChatColor.GREEN;
            case OVERHEALTH -> color = ChatColor.DARK_BLUE;
            case PHYSICALDAMAGE, PHYSICALRESIST -> color = ChatColor.DARK_RED;
            case FIREDAMAGE, FIRERESIST -> color = ChatColor.RED;
            case COLDDAMAGE, COLDRESIST -> color = ChatColor.AQUA;
            case EARTHDAMAGE, EARTHRESIST -> color = ChatColor.DARK_GREEN;
            case LIGHTNINGDAMAGE, LIGHTNINGRESIST -> color = ChatColor.YELLOW;
            case AIRDAMAGE, AIRRESIST -> color = ChatColor.GRAY;
            case NECROTICDAMAGE, NECROTICRESIST -> color = ChatColor.DARK_PURPLE;
            case CRITCHANCE, CRITDAMAGE -> color = ChatColor.BLUE;
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
            case RADIANTDAMAGE, RADIANTRESIST -> statEmoji = "✦";
            case NECROTICDAMAGE, NECROTICRESIST -> statEmoji = "\uD83C\uDF00";
            case PUREDAMAGE -> statEmoji = "\uD83D\uDCA2";
            case DEFENSE -> statEmoji = "\uD83D\uDEE1";
            case GUARD -> statEmoji = "⛨";
            case OVERHEALTH -> statEmoji = "\uD83D\uDC99";
            case EVASION -> statEmoji = "\uD83D\uDCA8";
            case CRITCHANCE, CRITDAMAGE -> statEmoji = "☠";
            default -> statEmoji = "";
        }

        return statEmoji;
    }
}
