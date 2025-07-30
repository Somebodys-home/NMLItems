package io.github.NoOne.nMLItems;

import org.bukkit.Material;

public enum ItemType {
    // weapon types
    SWORD,
    DAGGER,
    AXE,
    HAMMER,
    SPEAR,
    GLOVE,
    BOW,
    WAND,
    STAFF,
    CATALYST,

    // armor types
    LIGHT,
    MEDIUM,
    HEAVY,

    // offhand items
    SHIELD;

    public static String getItemTypeString(ItemType type) {
        switch (type) {
            case SWORD -> { return "sword"; }
            case DAGGER -> { return "dagger"; }
            case AXE -> { return "axe"; }
            case HAMMER -> { return "hammer"; }
            case SPEAR -> { return "spear"; }
            case GLOVE -> { return "glove"; }
            case BOW -> { return "bow"; }
            case WAND -> { return "wand"; }
            case STAFF -> { return "staff"; }
            case CATALYST -> { return "catalyst"; }

            case LIGHT -> { return "light"; }
            case MEDIUM -> { return "medium"; }
            case HEAVY -> { return "heavy"; }

            case SHIELD -> { return "shield"; }

            default -> { return ""; }
        }
    }

    public static Material getItemTypeMaterial(ItemType type) {
        switch (type) {
            case SWORD -> { return Material.IRON_SWORD; }
            case DAGGER -> { return Material.WOODEN_SWORD; }
            case AXE -> { return Material.IRON_AXE; }
            case HAMMER -> { return Material.MACE; }
            case SPEAR -> { return Material.TRIDENT; }
            case GLOVE -> { return Material.RED_WOOL; }
            case BOW -> { return Material.BOW; }
            case WAND -> { return Material.STICK; }
            case STAFF -> { return Material.WOODEN_HOE; }
            case CATALYST -> { return Material.ENCHANTED_BOOK; }
            case SHIELD -> { return Material.SHIELD; }
            default -> { return null; }
        }
    }
    
    public static Material getItemTypeMaterial(ItemType type, String identifier) {
        if (type == LIGHT) {
            if (identifier.equalsIgnoreCase("helmet")) return Material.LEATHER_HELMET;
            if (identifier.equalsIgnoreCase("chestplate")) return Material.LEATHER_CHESTPLATE;
            if (identifier.equalsIgnoreCase("leggings")) return Material.LEATHER_LEGGINGS;
            if (identifier.equalsIgnoreCase("boots")) return Material.LEATHER_BOOTS;
        } else if (type == MEDIUM) {
            if (identifier.equalsIgnoreCase("helmet")) return Material.CHAINMAIL_HELMET;
            if (identifier.equalsIgnoreCase("chestplate")) return Material.CHAINMAIL_CHESTPLATE;
            if (identifier.equalsIgnoreCase("leggings")) return Material.CHAINMAIL_LEGGINGS;
            if (identifier.equalsIgnoreCase("boots")) return Material.CHAINMAIL_BOOTS;
        } else if (type == HEAVY) {
            if (identifier.equalsIgnoreCase("helmet")) return Material.IRON_HELMET;
            if (identifier.equalsIgnoreCase("chestplate")) return Material.IRON_CHESTPLATE;
            if (identifier.equalsIgnoreCase("leggings")) return Material.IRON_LEGGINGS;
            if (identifier.equalsIgnoreCase("boots")) return Material.IRON_BOOTS;
        }

        return null;
    }

    public static ItemType getItemTypeFromString(String string) {
        switch (string) {
            case "sword" -> { return SWORD; }
            case "dagger" -> { return DAGGER; }
            case "axe" -> { return AXE; }
            case "hammer" -> { return HAMMER; }
            case "spear" -> { return SPEAR; }
            case "glove" -> { return GLOVE; }
            case "bow" -> { return BOW; }
            case "wand" -> { return WAND; }
            case "staff" -> { return STAFF; }
            case "catalyst" -> { return CATALYST; }
            case "light" -> { return LIGHT; }
            case "medium" -> { return MEDIUM; }
            case "heavy" -> { return HEAVY; }
            default -> { return null; }
        }
    }
}
