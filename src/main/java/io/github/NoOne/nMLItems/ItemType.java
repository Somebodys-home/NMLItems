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
        String itemTypeString;

        switch (type) {
            case SWORD -> itemTypeString = "Sword";
            case DAGGER -> itemTypeString = "Dagger";
            case AXE -> itemTypeString = "Axe";
            case HAMMER -> itemTypeString = "Hammer";
            case SPEAR -> itemTypeString = "Spear";
            case GLOVE -> itemTypeString = "Glove";
            case BOW -> itemTypeString = "Bow";
            case WAND -> itemTypeString = "Wand";
            case STAFF -> itemTypeString = "Staff";
            case CATALYST -> itemTypeString = "Catalyst";
            case LIGHT -> itemTypeString = "Light";
            case MEDIUM -> itemTypeString = "Medium";
            case HEAVY -> itemTypeString = "Heavy";
            case SHIELD -> itemTypeString = "Shield";
            default -> itemTypeString = "";
        }

        return itemTypeString;
    }

    public static Material getItemTypeMaterial(ItemType type) {
        Material itemTypeMaterial;

        switch (type) {
            case SWORD -> itemTypeMaterial = Material.IRON_SWORD;
            case DAGGER -> itemTypeMaterial = Material.WOODEN_SWORD;
            case AXE -> itemTypeMaterial = Material.IRON_AXE;
            case HAMMER -> itemTypeMaterial = Material.MACE;
            case SPEAR -> itemTypeMaterial = Material.TRIDENT;
            case GLOVE -> itemTypeMaterial = Material.RED_WOOL;
            case BOW -> itemTypeMaterial = Material.BOW;
            case WAND -> itemTypeMaterial = Material.STICK;
            case STAFF -> itemTypeMaterial = Material.WOODEN_HOE;
            case CATALYST -> itemTypeMaterial = Material.ENCHANTED_BOOK;
            default -> itemTypeMaterial = null;
        }

        return itemTypeMaterial;
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
        ItemType itemType;

        switch (string.toLowerCase()) {
            case "sword" -> itemType = SWORD;
            case "dagger" -> itemType = DAGGER;
            case "axe" -> itemType = AXE;
            case "hammer" -> itemType = HAMMER;
            case "spear" -> itemType = SPEAR;
            case "glove" -> itemType = GLOVE;
            case "bow" -> itemType = BOW;
            case "wand" -> itemType = WAND;
            case "staff" -> itemType = STAFF;
            case "catalyst" -> itemType = CATALYST;
            case "light" -> itemType = LIGHT;
            case "medium" -> itemType = MEDIUM;
            case "heavy" -> itemType = HEAVY;
            case "shield" -> itemType = SHIELD;
            default -> itemType = null;
        }

        return itemType;
    }
}
