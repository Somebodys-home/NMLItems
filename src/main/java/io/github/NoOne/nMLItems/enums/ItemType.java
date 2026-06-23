package io.github.NoOne.nMLItems.enums;

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

    // garden modifiers
    GARDEN_MODIFIER,

    // farming
    SEED,
    CROP,

    // cooking
    INGREDIENT;

    public static String toString(ItemType type) {
        return switch (type) {
            case SWORD -> "Sword";
            case DAGGER -> "Dagger";
            case AXE -> "Axe";
            case HAMMER -> "Hammer";
            case SPEAR -> "Spear";
            case GLOVE -> "Glove";
            case BOW -> "Bow";
            case WAND -> "Wand";
            case STAFF -> "Staff";
            case CATALYST -> "Catalyst";
            case HELMET -> "Helmet";
            case CHESTPLATE -> "Chestplate";
            case LEGGINGS -> "Leggings";
            case BOOTS -> "Boots";
            case LIGHT -> "Light";
            case MEDIUM -> "Medium";
            case HEAVY -> "Heavy";
            case SHIELD -> "Shield";
            case QUIVER -> "Quiver";
            case HOE -> "Hoe";
            case SEED -> "Seed";
            case CROP ->  "Crop";
            case GARDEN_MODIFIER -> "Garden_Modifier";
            case INGREDIENT ->  "Ingredient";
        };
    }

    public static Material toMaterial(ItemType type) {
        Material itemTypeMaterial;

        switch (type) {
            case SWORD -> itemTypeMaterial = Material.IRON_SWORD;
            case DAGGER -> itemTypeMaterial = Material.WOODEN_SWORD;
            case AXE -> itemTypeMaterial = Material.IRON_AXE;
            case HAMMER -> itemTypeMaterial = Material.MACE;
            case SPEAR -> itemTypeMaterial = Material.IRON_SPEAR;
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

    public static Material toMaterial(ItemType weight, ItemType type) {
        Material itemTypeMaterial = null;

        switch (weight) {
            case LIGHT -> {
                switch (type) {
                    case HELMET -> itemTypeMaterial = Material.LEATHER_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.LEATHER_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.LEATHER_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.LEATHER_BOOTS;
                }
            }
            case MEDIUM -> {
                switch (type) {
                    case HELMET -> itemTypeMaterial = Material.CHAINMAIL_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.CHAINMAIL_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.CHAINMAIL_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.CHAINMAIL_BOOTS;
                }
            }
            case HEAVY -> {
                switch (type) {
                    case HELMET -> itemTypeMaterial = Material.IRON_HELMET;
                    case CHESTPLATE -> itemTypeMaterial = Material.IRON_CHESTPLATE;
                    case LEGGINGS -> itemTypeMaterial = Material.IRON_LEGGINGS;
                    case BOOTS -> itemTypeMaterial = Material.IRON_BOOTS;
                }
            }
        }

        return itemTypeMaterial;
    }

    public static ItemType fromString(String string) {
        return switch (string.toLowerCase()) {
            case "sword" -> SWORD;
            case "dagger" -> DAGGER;
            case "axe" -> AXE;
            case "hammer" -> HAMMER;
            case "spear" -> SPEAR;
            case "glove" -> GLOVE;
            case "bow" -> BOW;
            case "wand" -> WAND;
            case "staff" -> STAFF;
            case "catalyst" -> CATALYST;
            case "helmet" -> HELMET;
            case "chestplate" -> CHESTPLATE;
            case "leggings" -> LEGGINGS;
            case "boots" -> BOOTS;
            case "light" -> LIGHT;
            case "medium" -> MEDIUM;
            case "heavy" -> HEAVY;
            case "shield" -> SHIELD;
            case "quiver" -> QUIVER;
            case "hoe" -> HOE;
            case "seed" -> SEED;
            case "crop" -> CROP;
            case "ingredient" -> INGREDIENT;
            default -> null;
        };
    }

    public static ItemType[] getAllWeaponTypes() {
        return new ItemType[]{SWORD, DAGGER, AXE, HAMMER, SPEAR, GLOVE, BOW, WAND, STAFF, CATALYST};
    }
}
