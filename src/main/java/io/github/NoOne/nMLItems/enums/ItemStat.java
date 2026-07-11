package io.github.NoOne.nMLItems.enums;

import org.bukkit.ChatColor;

public enum ItemStat {
    // damage stats
    PHYSICALDAMAGE,
    FIREDAMAGE,
    COLDDAMAGE,
    EARTHDAMAGE,
    LIGHTNINGDAMAGE,
    AIRDAMAGE,
    RADIANTDAMAGE,
    NECROTICDAMAGE,
    PUREDAMAGE,
    CRITCHANCE,
    CRITDAMAGE,

    // defense stats
    EVASION,
    DEFENSE,
    HEALTH,
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

    // gathering stats
    HARVEST,
    YIELD,
    ACRE,

    // misc stats
    SPEED;

    public static String toString(ItemStat stat) {
        return switch (stat) {
            case PHYSICALDAMAGE -> "Phys. Damage";
            case FIREDAMAGE -> "Fire Damage";
            case COLDDAMAGE -> "Cold Damage";
            case EARTHDAMAGE -> "Earth Damage";
            case LIGHTNINGDAMAGE -> "Light. Damage";
            case AIRDAMAGE -> "Air Damage";
            case RADIANTDAMAGE -> "Rad. Damage";
            case NECROTICDAMAGE -> "Necro. Damage";
            case PUREDAMAGE -> "Pure Damage";
            case EVASION -> "Evasion";
            case DEFENSE -> "Defense";
            case HEALTH -> "Health";
            case OVERHEALTH -> "Overhealth";
            case GUARD -> "Guard";
            case PHYSICALRESIST -> "Phys. Resist";
            case FIRERESIST -> "Fire Resist";
            case COLDRESIST -> "Cold Resist";
            case EARTHRESIST -> "Earth Resist";
            case LIGHTNINGRESIST -> "Light. Resist";
            case AIRRESIST -> "Air Resist";
            case RADIANTRESIST -> "Rad. Resist";
            case NECROTICRESIST -> "Necro. Resist";
            case CRITCHANCE -> "Crit. Chance";
            case CRITDAMAGE -> "Crit. Damage";
            case HARVEST -> "Harvest";
            case YIELD -> "Yield";
            case ACRE -> "Acre";
            case SPEED -> "Speed";
        };
    }

    public static ChatColor toChatColor(ItemStat stat) {
        return switch (stat) {
            case EVASION, RADIANTDAMAGE, PUREDAMAGE, RADIANTRESIST, GUARD, SPEED -> ChatColor.WHITE;
            case DEFENSE, HARVEST, YIELD, ACRE -> ChatColor.GREEN;
            case OVERHEALTH -> ChatColor.DARK_BLUE;
            case PHYSICALDAMAGE, PHYSICALRESIST -> ChatColor.DARK_RED;
            case FIREDAMAGE, FIRERESIST, HEALTH -> ChatColor.RED;
            case COLDDAMAGE, COLDRESIST -> ChatColor.AQUA;
            case EARTHDAMAGE, EARTHRESIST -> ChatColor.DARK_GREEN;
            case LIGHTNINGDAMAGE, LIGHTNINGRESIST -> ChatColor.YELLOW;
            case AIRDAMAGE, AIRRESIST -> ChatColor.GRAY;
            case NECROTICDAMAGE, NECROTICRESIST -> ChatColor.DARK_PURPLE;
            case CRITCHANCE, CRITDAMAGE -> ChatColor.BLUE;
        };
    }

    public static String toEmoji(ItemStat stat) {
        return switch (stat) {
            case PHYSICALDAMAGE, PHYSICALRESIST -> "⚔";
            case FIREDAMAGE, FIRERESIST -> "\uD83D\uDD25";
            case COLDDAMAGE, COLDRESIST -> "❄";
            case EARTHDAMAGE, EARTHRESIST -> "\uD83E\uDEA8";
            case LIGHTNINGDAMAGE, LIGHTNINGRESIST -> "\uD83D\uDDF2";
            case AIRDAMAGE, AIRRESIST -> "☁";
            case RADIANTDAMAGE, RADIANTRESIST, SPEED -> "✦";
            case NECROTICDAMAGE, NECROTICRESIST -> "\uD83C\uDF00";
            case PUREDAMAGE -> "\uD83D\uDCA2";
            case DEFENSE -> "\uD83D\uDEE1";
            case GUARD -> "⛨";
            case OVERHEALTH -> "\uD83D\uDC99";
            case EVASION -> "\uD83D\uDCA8";
            case CRITCHANCE, CRITDAMAGE -> "☠";
            case YIELD -> "\uD83E\uDD55";
            case ACRE -> "⚂";
            case HARVEST -> "\uD83E\uDEB4";
            case HEALTH -> "❤";
        };
    }
}
