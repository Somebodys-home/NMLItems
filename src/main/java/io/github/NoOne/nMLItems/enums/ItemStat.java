package io.github.NoOne.nMLItems.enums;

import io.github.NoOne.nMLDamages.DamageType;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;

public enum ItemStat {
    // damage stats
    PHYSICAL_DAMAGE,
    FIRE_DAMAGE,
    COLD_DAMAGE,
    EARTH_DAMAGE,
    LIGHTNING_DAMAGE,
    AIR_DAMAGE,
    RADIANT_DAMAGE,
    NECROTIC_DAMAGE,
    PURE_DAMAGE,
    CRIT_CHANCE,
    CRIT_DAMAGE,

    // defense stats
    EVASION,
    DEFENSE,
    HEALTH,
    MAX_HEALTH,
    OVERHEALTH,
    GUARD,
    PHYSICAL_RESIST,
    FIRE_RESIST,
    COLD_RESIST,
    EARTH_RESIST,
    LIGHTNING_RESIST,
    AIR_RESIST,
    RADIANT_RESIST,
    NECROTIC_RESIST,

    // gathering stats
    HARVEST,
    YIELD,
    ACRE,

    // misc stats
    SPEED;

    @Override
    public String toString() {
        return switch (this) {
            case PHYSICAL_DAMAGE -> "Phys. Damage";
            case FIRE_DAMAGE -> "Fire Damage";
            case COLD_DAMAGE -> "Cold Damage";
            case EARTH_DAMAGE -> "Earth Damage";
            case LIGHTNING_DAMAGE -> "Light. Damage";
            case AIR_DAMAGE -> "Air Damage";
            case RADIANT_DAMAGE -> "Rad. Damage";
            case NECROTIC_DAMAGE -> "Necro. Damage";
            case PURE_DAMAGE -> "Pure Damage";
            case EVASION -> "Evasion";
            case DEFENSE -> "Defense";
            case HEALTH -> "Health";
            case MAX_HEALTH ->  "Max. Health";
            case OVERHEALTH -> "Overhealth";
            case GUARD -> "Guard";
            case PHYSICAL_RESIST -> "Phys. Resist";
            case FIRE_RESIST -> "Fire Resist";
            case COLD_RESIST -> "Cold Resist";
            case EARTH_RESIST -> "Earth Resist";
            case LIGHTNING_RESIST -> "Light. Resist";
            case AIR_RESIST -> "Air Resist";
            case RADIANT_RESIST -> "Rad. Resist";
            case NECROTIC_RESIST -> "Necro. Resist";
            case CRIT_CHANCE -> "Crit. Chance";
            case CRIT_DAMAGE -> "Crit. Damage";
            case HARVEST -> "Harvest";
            case YIELD -> "Yield";
            case ACRE -> "Acre";
            case SPEED -> "Speed";
        };
    }

    public String toChatColor() {
        return switch (this) {
            case EVASION, RADIANT_DAMAGE, PURE_DAMAGE, RADIANT_RESIST, GUARD, SPEED -> "§f";
            case DEFENSE, HARVEST, YIELD, ACRE -> "§a";
            case PHYSICAL_DAMAGE, PHYSICAL_RESIST -> "§4";
            case FIRE_DAMAGE, FIRE_RESIST, HEALTH, MAX_HEALTH -> "§c";
            case COLD_DAMAGE, COLD_RESIST -> "§b";
            case EARTH_DAMAGE, EARTH_RESIST -> "§2";
            case LIGHTNING_DAMAGE, LIGHTNING_RESIST -> "§e";
            case AIR_DAMAGE, AIR_RESIST -> "§7";
            case NECROTIC_DAMAGE, NECROTIC_RESIST -> "§5";
            case OVERHEALTH, CRIT_CHANCE, CRIT_DAMAGE -> "§9";
        };
    }

    public String toEmoji() {
        return switch (this) {
            case PHYSICAL_DAMAGE, PHYSICAL_RESIST -> "⚔";
            case FIRE_DAMAGE, FIRE_RESIST -> "\uD83D\uDD25";
            case COLD_DAMAGE, COLD_RESIST -> "❄";
            case EARTH_DAMAGE, EARTH_RESIST -> "\uD83E\uDEA8";
            case LIGHTNING_DAMAGE, LIGHTNING_RESIST -> "\uD83D\uDDF2";
            case AIR_DAMAGE, AIR_RESIST -> "☁";
            case RADIANT_DAMAGE, RADIANT_RESIST, SPEED -> "✦";
            case NECROTIC_DAMAGE, NECROTIC_RESIST -> "\uD83C\uDF00";
            case PURE_DAMAGE -> "\uD83D\uDCA2";
            case DEFENSE -> "\uD83D\uDEE1";
            case GUARD -> "⛨";
            case OVERHEALTH -> "\uD83D\uDC99";
            case EVASION -> "\uD83D\uDCA8";
            case CRIT_CHANCE, CRIT_DAMAGE -> "☠";
            case YIELD -> "\uD83E\uDD55";
            case ACRE -> "⚂";
            case HARVEST -> "\uD83E\uDEB4";
            case HEALTH, MAX_HEALTH -> "❤";
        };
    }

    public PlayerStat toPlayerStat() {
        return switch (this) {
            case PHYSICAL_DAMAGE -> PlayerStat.PHYSICAL_DAMAGE;
            case FIRE_DAMAGE -> PlayerStat.FIRE_DAMAGE;
            case COLD_DAMAGE -> PlayerStat.COLD_DAMAGE;
            case EARTH_DAMAGE -> PlayerStat.EARTH_DAMAGE;
            case LIGHTNING_DAMAGE -> PlayerStat.LIGHTNING_DAMAGE;
            case AIR_DAMAGE -> PlayerStat.AIR_DAMAGE;
            case RADIANT_DAMAGE -> PlayerStat.RADIANT_DAMAGE;
            case NECROTIC_DAMAGE -> PlayerStat.NECROTIC_DAMAGE;
            case PURE_DAMAGE -> PlayerStat.PURE_DAMAGE;
            case CRIT_CHANCE -> PlayerStat.CRIT_CHANCE;
            case CRIT_DAMAGE -> PlayerStat.CRIT_DAMAGE;
            case EVASION -> PlayerStat.EVASION;
            case DEFENSE -> PlayerStat.DEFENSE;
            case MAX_HEALTH -> PlayerStat.MAX_HEALTH;
            case OVERHEALTH -> PlayerStat.MAX_OVERHEALTH;
            case GUARD -> PlayerStat.GUARD;
            case PHYSICAL_RESIST -> PlayerStat.PHYSICAL_RESIST;
            case FIRE_RESIST -> PlayerStat.FIRE_RESIST;
            case COLD_RESIST -> PlayerStat.COLD_RESIST;
            case EARTH_RESIST -> PlayerStat.EARTH_RESIST;
            case LIGHTNING_RESIST -> PlayerStat.LIGHTNING_RESIST;
            case AIR_RESIST -> PlayerStat.AIR_RESIST;
            case RADIANT_RESIST -> PlayerStat.RADIANT_RESIST;
            case NECROTIC_RESIST -> PlayerStat.NECROTIC_RESIST;
            case HARVEST -> PlayerStat.HARVEST;
            case YIELD -> PlayerStat.YIELD;
            case ACRE -> PlayerStat.ACRE;
            case SPEED -> PlayerStat.SPEED;
            case HEALTH -> PlayerStat.HEALTH;
        };
    }

    public DamageType toDamageType() {
        return switch (this) {
            case PHYSICAL_DAMAGE -> DamageType.PHYSICAL;
            case FIRE_DAMAGE -> DamageType.FIRE;
            case COLD_DAMAGE -> DamageType.COLD;
            case EARTH_DAMAGE -> DamageType.EARTH;
            case LIGHTNING_DAMAGE -> DamageType.LIGHTNING;
            case AIR_DAMAGE -> DamageType.AIR;
            case RADIANT_DAMAGE -> DamageType.RADIANT;
            case NECROTIC_DAMAGE -> DamageType.NECROTIC;
            case PURE_DAMAGE -> DamageType.PURE;
            default -> null;
        };
    }

    // for food
    public int getSeconds() {
        return switch (this) {
            case PHYSICAL_DAMAGE -> 300;
            case SPEED -> 150;
            default -> 0;
        };
    }
}
