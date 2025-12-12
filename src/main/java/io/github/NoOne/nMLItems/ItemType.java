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
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    LIGHT,
    MEDIUM,
    HEAVY,

    // offhand items
    SHIELD,
    QUIVER,

    // tools
    HOE,

    // misc
    SEED,
    CROP;

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
            case HELMET -> itemTypeString = "Helmet";
            case CHESTPLATE -> itemTypeString = "Chestplate";
            case LEGGINGS -> itemTypeString = "Leggings";
            case BOOTS -> itemTypeString = "Boots";
            case LIGHT -> itemTypeString = "Light";
            case MEDIUM -> itemTypeString = "Medium";
            case HEAVY -> itemTypeString = "Heavy";
            case SHIELD -> itemTypeString = "Shield";
            case QUIVER -> itemTypeString = "Quiver";
            case HOE -> itemTypeString = "Hoe";
            case SEED -> itemTypeString = "Seed";
            case CROP ->  itemTypeString = "Crop";
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
            case SHIELD -> itemTypeMaterial = Material.SHIELD;
            case QUIVER -> itemTypeMaterial = Material.ARROW;
            case HOE -> itemTypeMaterial = Material.IRON_HOE;
            default -> itemTypeMaterial = null;
        }

        return itemTypeMaterial;
    }

    public static Material getItemTypeMaterial(ItemType type, ItemType type2) {
        Material itemTypeMaterial = null;

        switch (type) {
            case LIGHT -> {
                switch (type2) {
                    case HELMET -> itemTypeMaterial = Material.LEATHER_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.LEATHER_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.LEATHER_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.LEATHER_BOOTS;
                }
            }
            case MEDIUM -> {
                switch (type2) {
                    case HELMET -> itemTypeMaterial = Material.CHAINMAIL_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.CHAINMAIL_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.CHAINMAIL_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.CHAINMAIL_BOOTS;
                }
            }
            case HEAVY -> {
                switch (type2) {
                    case HELMET -> itemTypeMaterial = Material.IRON_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.IRON_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.IRON_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.IRON_BOOTS;
                }
            }
        }

        return itemTypeMaterial;
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
            case "helmet" -> itemType = HELMET;
            case "chestplate" -> itemType = CHESTPLATE;
            case "leggings" -> itemType = LEGGINGS;
            case "boots" -> itemType = BOOTS;
            case "light" -> itemType = LIGHT;
            case "medium" -> itemType = MEDIUM;
            case "heavy" -> itemType = HEAVY;
            case "shield" -> itemType = SHIELD;
            case "quiver" -> itemType = QUIVER;
            case "hoe" -> itemType = HOE;
            case "seed" -> itemType = SEED;
            case "crop" -> itemType = CROP;
            default -> itemType = null;
        }

        return itemType;
    }

    public static ItemType[] getAllWeaponTypes() {
        return new ItemType[]{SWORD, DAGGER, AXE, HAMMER, SPEAR, GLOVE, BOW, WAND, STAFF, CATALYST};
    }
}
