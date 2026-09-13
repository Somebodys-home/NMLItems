package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.enums.*;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class ItemSystem {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static SkillSetManager skillSetManager = nmlItems.getSkillSetManager();
    private static NamespacedKey itemTypeKey = new NamespacedKey(nmlItems, "item_type"); // item types stored as (type)/(type)/...
    private static NamespacedKey itemStatsKey = new NamespacedKey(nmlItems, "item_stats"); // stats stored as (stat)-##/(stat)-##/...
    private static NamespacedKey originalNameKey = new NamespacedKey(nmlItems, "original_name");
    private static NamespacedKey usableKey = new NamespacedKey(nmlItems, "usable");
    private static NamespacedKey rarityKey = new NamespacedKey(nmlItems, "rarity");
    private static NamespacedKey levelKey = new NamespacedKey(nmlItems, "level");
    private static NamespacedKey starsKey = new NamespacedKey(nmlItems, "stars");
    private static NamespacedKey seedKey = new NamespacedKey(nmlItems, "seed");
    private static NamespacedKey cropKey = new NamespacedKey(nmlItems, "crop");
    private static NamespacedKey gardenModifierKey = new NamespacedKey(nmlItems, "garden_modifier");
    private static NamespacedKey ingredientKey = new NamespacedKey(nmlItems, "ingredient");
    private static NamespacedKey filledWithKey = new NamespacedKey(nmlItems, "filled_with");
    private static NamespacedKey servingsKey = new NamespacedKey(nmlItems, "servings");
    private static NamespacedKey foodTypeKey = new NamespacedKey(nmlItems, "food_type");

    public static void setStat(ItemStack itemStack, ItemStat itemStat, double amount) {
        if (!hasStat(itemStack, itemStat)) {
            if (amount == (int) amount) {
                setStat(itemStack, itemStat, (int) amount);
            } else {
                ItemMeta meta = itemStack.getItemMeta();
                PersistentDataContainer pdc = meta.getPersistentDataContainer();

                if (!pdc.has(itemStatsKey)) { // logic changes if the item alr has stats set or not
                    String statString = ItemStat.toString(itemStat) + "-" + amount;

                    pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
                } else {
                    String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);

                    statString += "/" + ItemStat.toString(itemStat) + "-" + amount;
                    pdc.remove(itemStatsKey);
                    pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
                }

                itemStack.setItemMeta(meta);
            }
        }
    }

    public static void setStat(ItemStack itemStack, ItemStat itemStat, int amount) {
        if (!hasStat(itemStack, itemStat)) {
            ItemMeta meta = itemStack.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            if (!pdc.has(itemStatsKey)) { // logic changes if the item alr has stats set or not
                String statString = ItemStat.toString(itemStat) + "-" + amount;

                pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
            } else {
                String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);

                statString += "/" + ItemStat.toString(itemStat) + "-" + amount;
                pdc.remove(itemStatsKey);
                pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
            }

            itemStack.setItemMeta(meta);
        }
    }

    public static void setStats(ItemStack itemStack, HashMap<ItemStat, ? extends Number> stats) {
        for (Map.Entry<ItemStat, ? extends Number> entry : stats.entrySet()) {
            setStat(itemStack, entry.getKey(), entry.getValue().doubleValue());
        }
    }

    public static void setItemType(ItemStack itemStack, ItemType itemType) {
        if (!isItemType(itemStack, itemType)) {
            ItemMeta meta = itemStack.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            if (!pdc.has(itemTypeKey)) { // logic changes if the item alr has its type set or not
                pdc.set(itemTypeKey, PersistentDataType.STRING, ItemType.toString(itemType));

            } else {
                String typeString = pdc.get(itemTypeKey, PersistentDataType.STRING);

                typeString += "/" + ItemType.toString(itemType);
                pdc.remove(itemTypeKey);
                pdc.set(itemTypeKey, PersistentDataType.STRING, typeString);
            }

            itemStack.setItemMeta(meta);
        }
    }

    public static void setItemTypes(ItemStack itemStack, List<ItemType> itemTypes) {
        for (ItemType itemType : itemTypes) {
            setItemType(itemStack, itemType);
        }
    }

    public static void setOriginalName(ItemStack itemStack, String originalName) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(originalNameKey, PersistentDataType.STRING, originalName);
        itemStack.setItemMeta(meta);
    }

    public static void setUsability(ItemStack itemStack, boolean usable) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(usableKey, PersistentDataType.BOOLEAN, usable);
        itemStack.setItemMeta(meta);
    }

    public static void setRarity(ItemStack itemStack, ItemRarity itemRarity) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(rarityKey, PersistentDataType.STRING, ItemRarity.toString(itemRarity));
        itemStack.setItemMeta(meta);
    }

    public static void setLevel(ItemStack itemStack, int level) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(levelKey, PersistentDataType.INTEGER, level);
        itemStack.setItemMeta(meta);
    }

    public static void setStars(ItemStack itemStack, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(starsKey, PersistentDataType.DOUBLE, stars);
        itemStack.setItemMeta(meta);
    }

    public static void setSeedType(ItemStack itemStack, SeedType seedType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(seedKey, PersistentDataType.STRING, SeedType.toString(seedType));
        itemStack.setItemMeta(meta);
    }

    public static void setCropType(ItemStack itemStack, CropType cropType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(cropKey, PersistentDataType.STRING, CropType.toString(cropType));
        itemStack.setItemMeta(meta);
    }

    public static void setGardenModifier(ItemStack itemStack, GardenModifier gardenModifier) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(gardenModifierKey, PersistentDataType.STRING, GardenModifier.toString(gardenModifier));
        itemStack.setItemMeta(meta);
    }

    public static void setIngredientType(ItemStack itemStack, IngredientType ingredientType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ingredientKey, PersistentDataType.STRING, IngredientType.toString(ingredientType));
        itemStack.setItemMeta(meta);
    }

    // assumed: "filledWith" is a string of bytes as an ItemStack[]
    public static void setFilledWithItems(ItemStack itemStack, String filledWith) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(filledWithKey, PersistentDataType.STRING, filledWith);
        itemStack.setItemMeta(meta);
    }

    public static void setServings(ItemStack itemStack, int servings) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(servingsKey, PersistentDataType.INTEGER, servings);
        itemStack.setItemMeta(meta);
    }

    public static void setFoodType(ItemStack itemStack, FoodType foodType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(foodTypeKey, PersistentDataType.STRING, FoodType.toString(foodType));
        itemStack.setItemMeta(meta);
    }

    public static void updateLoreWithStat(ItemStack itemStack, ItemStat itemStat, double value) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore()){{
            add(makeItemStatString(itemStat, value));
        }};

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
    }

    public static void updateLoreWithStat(ItemStack itemStack, ItemStat itemStat, int value) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore()){{
            add(makeItemStatString(itemStat, value));
        }};

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
    }

    public static void updateLoreWithStats(ItemStack itemStack, HashMap<ItemStat, ? extends Number> itemStats) {
        for (Map.Entry<ItemStat, ? extends Number> entry : itemStats.entrySet()) {
            updateLoreWithStat(itemStack, entry.getKey(), entry.getValue().doubleValue());
        }
    }

    // updates the lore with the items stats while respecting its final line
    // (which will be its material stars)
    public static void updateMaterialItemLoreWithStats(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(meta.getLore());
        String starString = lore.getLast();
        LinkedHashMap<ItemStat, Double> sortedStats = sortStats(getAllStats(itemStack));

        lore.removeLast();

        for (Map.Entry<ItemStat, Double> entry : sortedStats.entrySet()) {
            double value = entry.getValue();

            if (value == (int) value) {
                lore.add(makeItemStatString(entry.getKey(), (int) value));
            } else {
                lore.add(makeItemStatString(entry.getKey(), entry.getValue()));
            }
        }

        lore.addAll(List.of("", starString));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void updateUnusableItemName(ItemStack itemStack, boolean usable) {
        ItemMeta meta = itemStack.getItemMeta();
        String originalName = getOriginalItemName(itemStack);
        String editedName;

        if (!usable) {
            editedName = "§c§m" + ChatColor.stripColor(originalName);
        } else {
            editedName = originalName;
        }

        meta.setDisplayName(editedName);
        itemStack.setItemMeta(meta);
    }

    public static void turnIntoDisplayItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(itemMeta.getLore());
        int index = 0;

        for (String line : lore) {
            if (index == lore.size() - 1) {
                lore.set(index, "§6 < §kaaaaa §r§6>");
            } else if (!line.isEmpty()) {
                String originalColor = line.substring(0, 2);
                String replacementLine = ChatColor.stripColor(line);

                replacementLine = replacementLine.replaceAll("\\d+", "§kX§r§8" + originalColor);
                replacementLine = originalColor + replacementLine;
                lore.set(index, replacementLine);
            }

            index++;
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addLoreToItem(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<>(itemMeta.getLore()){{
            addAll(lore);
        }};

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
    }

    public static void usableItemCheck(ItemStack itemStack, Player player) {
        boolean usable = isItemUsable(itemStack, player);
        boolean changed = isItemUsable(itemStack) != usable;

        if (changed) { // we don't care if nothing changed about the item's usability
            if (!usable) {
                addLoreToItem(itemStack, List.of(
                        "",
                        "§cYou cannot use",
                        "§cthis item!"
                ));
            } else {
                ItemMeta itemMeta = itemStack.getItemMeta();
                ArrayList<String> itemLore = new ArrayList<>(itemMeta.getLore());

                itemLore.removeAll(List.of(
                        "",
                        "§cYou cannot use",
                        "§cthis item!"
                ));

                itemMeta.setLore(itemLore);
                itemStack.setItemMeta(itemMeta);
            }

            updateUnusableItemName(itemStack, usable);
            setUsability(itemStack, usable);
        }
    }

    public static String getOriginalItemName(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(originalNameKey)) {
                return pdc.get(originalNameKey, PersistentDataType.STRING);
            }
        }

        return null;
    }

    public static String makeItemStatString(ItemStat itemStat, int value) {
        return switch (itemStat) {
            case CRITCHANCE, CRITDAMAGE -> ItemStat.toChatColor(itemStat) + "+ " + value + "% " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
            default ->  ItemStat.toChatColor(itemStat) + "+ " + value + " " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
        };
    }

    public static String makeItemStatString(ItemStat itemStat, double value) {
        if (value == (int) value) {
            return makeItemStatString(itemStat, (int) value);
        } else {
            return switch (itemStat) {
                case CRITCHANCE, CRITDAMAGE -> ItemStat.toChatColor(itemStat) + "+ " + value + "% " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
                default ->  ItemStat.toChatColor(itemStat) + "+ " + value + " " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
            };
        }
    }

    public static int getLevel(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(levelKey)) {
                return pdc.get(levelKey, PersistentDataType.INTEGER);
            }
        }

        return 0;
    }

    public static int getServings(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(servingsKey)) {
                return pdc.get(servingsKey, PersistentDataType.INTEGER);
            }
        }

        return 0;
    }

    public static double getStatValue(ItemStack itemStack, ItemStat itemStat) {
        if (hasStat(itemStack, itemStat)) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();
            String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);
            String[] statSplits = statString.split("/"); // stats on the key are stored as (stat)-##/(stat)-## etc. so each split is its own stat and value

            for (String statSplit : statSplits) {
                if (statSplit.contains(ItemStat.toString(itemStat))) { // if that split is the one for the stat
                    return Double.parseDouble(statSplit.split("-")[1]);
                }
            }
        }

        return 0;
    }

    public static double getTotalDamageOfItem(ItemStack itemStack) {
        HashMap<ItemStat, Double> damageStats = getAllDamageStats(itemStack);
        double totalDamage = 0;

        for (Map.Entry<ItemStat, Double> damageEntry : damageStats.entrySet()) {
            totalDamage += damageEntry.getValue();
        }

        return totalDamage;
    }

    public static double getStars(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(starsKey, PersistentDataType.DOUBLE)) {
                return pdc.get(starsKey, PersistentDataType.DOUBLE);
            }
        }

        return 0;
    }

    public static boolean hasStat(ItemStack itemStack, ItemStat itemStat) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(itemStatsKey, PersistentDataType.STRING)) {
                return pdc.get(itemStatsKey, PersistentDataType.STRING).contains(ItemStat.toString(itemStat));
            }

        }

        return false;
    }

    public static boolean isItemUsable(ItemStack itemStack, Player player) {
        int itemLevel = getLevel(itemStack);

        if (isItemType(itemStack, HOE)) {
            return skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getFarmingLevel() >= itemLevel;
        } else {
            return skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getCombatLevel() >= itemLevel;
        }
    }

    public static boolean isItemUsable(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(usableKey)) {
                return pdc.get(usableKey, PersistentDataType.BOOLEAN);
            }
        }

        return false;
    }

    public static boolean isItemType(ItemStack itemStack, ItemType itemType) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(itemTypeKey, PersistentDataType.STRING)) {
                return pdc.get(itemTypeKey, PersistentDataType.STRING).contains(ItemType.toString(itemType));
            }
        }

        return false;
    }

    public static boolean hasDamageStats(ItemStack itemStack) {
        return hasStat(itemStack, ItemStat.PHYSICALDAMAGE) ||
                hasStat(itemStack, ItemStat.FIREDAMAGE) ||
                hasStat(itemStack, ItemStat.COLDDAMAGE) ||
                hasStat(itemStack, ItemStat.EARTHDAMAGE) ||
                hasStat(itemStack, ItemStat.LIGHTNINGDAMAGE) ||
                hasStat(itemStack, ItemStat.AIRDAMAGE) ||
                hasStat(itemStack, ItemStat.RADIANTDAMAGE) ||
                hasStat(itemStack, ItemStat.NECROTICDAMAGE) ||
                hasStat(itemStack, ItemStat.PUREDAMAGE);
    }

    public static boolean isEquippable(ItemStack itemStack) {
        return isItemType(itemStack, HELMET) ||
                isItemType(itemStack, CHESTPLATE) ||
                isItemType(itemStack, LEGGINGS) ||
                isItemType(itemStack, BOOTS) ||
                isItemType(itemStack, SHIELD) ||
                isItemType(itemStack, QUIVER);
    }

    public static boolean isWeapon(ItemStack itemStack) {
        return isItemType(itemStack, SWORD) ||
                isItemType(itemStack, DAGGER) ||
                isItemType(itemStack, AXE) ||
                isItemType(itemStack, HAMMER) ||
                isItemType(itemStack, SPEAR) ||
                isItemType(itemStack, GLOVE) ||
                isItemType(itemStack, BOW) ||
                isItemType(itemStack, WAND) ||
                isItemType(itemStack, STAFF) ||
                isItemType(itemStack, CATALYST);
    }

    public static boolean hasLevelKey(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(levelKey);
    }

    public static boolean hasOriginalNameKey(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(originalNameKey);
    }

    public static boolean hasFilledWithKey(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(filledWithKey);
    }

    public static HashMap<ItemStat, Double> getAllStats(ItemStack itemStack) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat itemStat : ItemStat.values()) {
            if (hasStat(itemStack, itemStat)) {
                stats.put(itemStat, getStatValue(itemStack, itemStat));
            }
        }

        return stats;
    }

    public static HashMap<String, Double> convertItemStatsToPlayerStats(ItemStack itemStack) {
        return new HashMap<>(){{
            for (Entry<ItemStat, Double> statEntry : getAllStats(itemStack).entrySet()) {
                put(ItemStat.toString(statEntry.getKey()).toLowerCase().replaceAll(" ", ""), statEntry.getValue());
            }
        }};
    }

    public static HashMap<ItemStat, Double> getAllDamageStats(ItemStack itemStack) {
        HashMap<ItemStat, Double> damageStats = new HashMap<>();

        if (!hasDamageStats(itemStack)) return damageStats;

        for (ItemStat itemStat : ItemStat.values()) {
            if (hasStat(itemStack, itemStat)) {
                damageStats.put(itemStat, getStatValue(itemStack, itemStat));
            }
        }

        return damageStats;
    }

    public static HashMap<ItemStat, Double> multiplyAllDamageStats(ItemStack itemStack, double multiplier) {
        HashMap<ItemStat, Double> multipliedDamage = getAllDamageStats(itemStack);

        for (Map.Entry<ItemStat, Double> damageEntry : multipliedDamage.entrySet()) {
            damageEntry.setValue(damageEntry.getValue() * multiplier);
        }

        return multipliedDamage;
    }

    // sorts stats from highest to lowest value
    public static <T extends Number & Comparable<T>> LinkedHashMap<ItemStat, T> sortStats(HashMap<ItemStat, T> itemStats) {
        LinkedHashMap<ItemStat, T> sortedStats = new LinkedHashMap<>();
        List<Map.Entry<ItemStat, T>> entryList = new ArrayList<>(itemStats.entrySet());

        entryList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<ItemStat, T> entry : entryList) {
            sortedStats.put(entry.getKey(), entry.getValue());
        }

        return sortedStats;
    }

    public static ArrayList<ItemType> getAllItemTypes(ItemStack itemStack) {
        ArrayList<ItemType> itemTypes = new ArrayList<>();

        if (itemStack.hasItemMeta()) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            if (pdc.has(itemTypeKey)) {
                for (String string : pdc.get(itemTypeKey, PersistentDataType.STRING).split("/")) {
                    itemTypes.add(ItemType.fromString(string));
                }
            }
        }

        return itemTypes;
    }

    public static ItemRarity getItemRarity(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(rarityKey, PersistentDataType.STRING)) {
            return ItemRarity.fromString(pdc.get(itemTypeKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static IngredientType getIngredientType(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(ingredientKey)) {
            return IngredientType.fromString(pdc.get(ingredientKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static SeedType getSeedType(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(seedKey)) {
            return SeedType.fromString(pdc.get(seedKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static CropType getCropType(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(cropKey)) {
            return CropType.fromString(pdc.get(cropKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static GardenModifier getGardenModifierType(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(gardenModifierKey)) {
            return GardenModifier.fromString(pdc.get(gardenModifierKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static FoodType getFoodType(ItemStack itemStack) {
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        if (pdc.has(foodTypeKey)) {
            return FoodType.fromString(pdc.get(foodTypeKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static NamespacedKey getFilledWithKey() {
        return filledWithKey;
    }
}
