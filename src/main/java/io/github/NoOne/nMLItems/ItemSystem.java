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
import java.util.stream.Collectors;

import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class ItemSystem {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static SkillSetManager skillSetManager = nmlItems.getSkillSetManager();
    private static NamespacedKey itemTypeKey = new NamespacedKey(nmlItems, "item_type"); // item types stored as (type)/(type)/...
    private static NamespacedKey itemStatsKey = new NamespacedKey(nmlItems, "item_stats"); // stats stored as (stat)-##/(stat)-##/...
    private static NamespacedKey secondaryTypeKey = new NamespacedKey(nmlItems, "second_item_type");
    private static NamespacedKey originalNameKey = new NamespacedKey(nmlItems, "original_name");
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

    public static void setStat(ItemStack item, ItemStat itemStat, double amount) {
        if (!hasStat(item, itemStat)) {
            if (amount == (int) amount) {
                setStat(item, itemStat, (int) amount);
            } else {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer pdc = meta.getPersistentDataContainer();

                if (!pdc.has(itemStatsKey)) {
                    String statString = ItemStat.toString(itemStat) + "-" + amount;

                    pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
                } else {
                    String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);

                    statString += "/" + ItemStat.toString(itemStat) + "-" + amount;
                    pdc.remove(itemStatsKey);
                    pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
                }

                item.setItemMeta(meta);
            }
        }
    }

    public static void setStat(ItemStack item, ItemStat itemStat, int amount) {
        if (!hasStat(item, itemStat)) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            if (!pdc.has(itemStatsKey)) {
                String statString = ItemStat.toString(itemStat) + "-" + amount;

                pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
            } else {
                String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);

                statString += "/" + ItemStat.toString(itemStat) + "-" + amount;
                pdc.remove(itemStatsKey);
                pdc.set(itemStatsKey, PersistentDataType.STRING, statString);
            }

            item.setItemMeta(meta);
        }
    }

    public static void setStats(ItemStack itemStack, HashMap<ItemStat, ? extends Number> stats) {
        for (Map.Entry<ItemStat, ? extends Number> entry : stats.entrySet()) {
            setStat(itemStack, entry.getKey(), entry.getValue().doubleValue());
        }
    }

    public static void setItemType(ItemStack itemStack, ItemType itemType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemTypeKey, PersistentDataType.STRING, ItemType.toString(itemType));
        itemStack.setItemMeta(meta);
    }

    public static void setSecondaryType(ItemStack itemStack, ItemType itemType) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemTypeKey, PersistentDataType.STRING, ItemType.toString(itemType));
        itemStack.setItemMeta(meta);
    }

    public static void setOriginalName(ItemStack itemStack, String originalName) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(originalNameKey, PersistentDataType.STRING, originalName);
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

    public static void updateLoreWithStat(ItemStack item, ItemStat itemStat, double value) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore()){{
            add(makeItemStatString(itemStat, value));
        }};

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static void updateLoreWithStat(ItemStack item, ItemStat itemStat, int value) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore()){{
            add(makeItemStatString(itemStat, value));
        }};

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static void updateLoreWithStats(ItemStack item, HashMap<ItemStat, ? extends Number> itemStats) {
        for (Map.Entry<ItemStat, ? extends Number> entry : itemStats.entrySet()) {
            updateLoreWithStat(item, entry.getKey(), entry.getValue().doubleValue());
        }
    }

    public static void updateEquipmentLoreWithStats(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> originalLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> addedLore = new ArrayList<>();
        HashMap<ItemStat, Double> itemStats = getAllStats(itemStack);

        itemStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Descending sort
                .forEachOrdered(entry -> {
                    ItemStat itemStat = entry.getKey();
                    double value = entry.getValue();
                    int valueInt = (int) value;

                    switch (itemStat) {
                        case CRITCHANCE, CRITDAMAGE -> addedLore.add(ItemStat.toChatColor(itemStat) + "+ " + valueInt + "% " +
                                ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat));
                        default ->  addedLore.add(ItemStat.toChatColor(itemStat) + "+ " + valueInt + " " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat));
                    }
                });

        originalLore.addAll(addedLore);
        meta.setLore(originalLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
    }

    // updates the lore with the items stats while respecting its final line
    // (which will be its material stars)
    public static void updateMaterialItemLoreWithStats(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(meta.getLore());
        String starString = lore.getLast();
        LinkedHashMap<ItemStat, Double> sortedStats = getAllStats(itemStack).entrySet().stream()
                .sorted(Map.Entry.<ItemStat, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, _) -> e1,
                        LinkedHashMap::new
                ));

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

    public static void updateUnusableItemName(ItemStack item, boolean usable) {
        ItemMeta meta = item.getItemMeta();
        String originalName = getOriginalItemName(item);
        String editedName;

        if (!usable) {
            editedName = "§c§m" + ChatColor.stripColor(originalName);
        } else {
            editedName = originalName;
        }

        meta.setDisplayName(editedName);
        item.setItemMeta(meta);
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

    public static String getOriginalItemName(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(originalNameKey, PersistentDataType.STRING)) {
            return null;
        }

        return pdc.get(originalNameKey, PersistentDataType.STRING);
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

    public static int getLevel(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(levelKey)) {
            return pdc.get(levelKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public static int getServings(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(servingsKey)) {
            return pdc.get(servingsKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public static double getStatValue(ItemStack item, ItemStat itemStat) {
        if (hasStat(item, itemStat)) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
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

    public static double getTotalDamageOfItem(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = getAllDamageStats(item);
        double totalDamage = 0;

        for (Map.Entry<ItemStat, Double> damageEntry : damageStats.entrySet()) {
            totalDamage += damageEntry.getValue();
        }

        return totalDamage;
    }

    public static double getStars(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(starsKey)) {
            return pdc.get(starsKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public static boolean hasStat(ItemStack item, ItemStat itemStat) {
        if (item == null || !item.hasItemMeta()) return false;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(itemStatsKey, PersistentDataType.STRING)) {
            String statString = pdc.get(itemStatsKey, PersistentDataType.STRING);

            return statString.contains(ItemStat.toString(itemStat));
        }

        return false;
    }

    public static boolean isItemUsable(ItemStack item, Player player) {
        if (getItemType(item) == null) {
            return false;
        }

        int itemLevel = getLevel(item);

        return switch (getItemType(item)) {
            case HOE -> skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getFarmingLevel() >= itemLevel;
            default -> skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getCombatLevel() >= itemLevel;
        };
    }

    public static boolean isItemType(ItemStack itemStack, ItemType itemType) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;
        
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();

        if (persistentDataContainer.has(itemTypeKey, PersistentDataType.STRING)) {
            String keyValue = persistentDataContainer.get(itemTypeKey, PersistentDataType.STRING);

            if (keyValue.equals(ItemType.toString(itemType))) {
                return true;
            } else if (persistentDataContainer.has(secondaryTypeKey, PersistentDataType.STRING)) {
                keyValue = persistentDataContainer.get(secondaryTypeKey, PersistentDataType.STRING);

                if (keyValue.equals(ItemType.toString(itemType))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasDamageStats(ItemStack item) {
        return hasStat(item, ItemStat.PHYSICALDAMAGE) ||
                hasStat(item, ItemStat.FIREDAMAGE) ||
                hasStat(item, ItemStat.COLDDAMAGE) ||
                hasStat(item, ItemStat.EARTHDAMAGE) ||
                hasStat(item, ItemStat.LIGHTNINGDAMAGE) ||
                hasStat(item, ItemStat.AIRDAMAGE) ||
                hasStat(item, ItemStat.RADIANTDAMAGE) ||
                hasStat(item, ItemStat.NECROTICDAMAGE) ||
                hasStat(item, ItemStat.PUREDAMAGE);
    }

    public static boolean isEquippable(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        return isItemType(item, HELMET) ||
                isItemType(item, CHESTPLATE) ||
                isItemType(item, LEGGINGS) ||
                isItemType(item, BOOTS) ||
                isItemType(item, SHIELD) ||
                isItemType(item, QUIVER);
    }

    public static boolean isWeapon(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        return isItemType(item, SWORD) ||
                isItemType(item, DAGGER) ||
                isItemType(item, AXE) ||
                isItemType(item, HAMMER) ||
                isItemType(item, SPEAR) ||
                isItemType(item, GLOVE) ||
                isItemType(item, BOW) ||
                isItemType(item, WAND) ||
                isItemType(item, STAFF) ||
                isItemType(item, CATALYST);
    }

    public static boolean hasLevelKey(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(levelKey);
    }

    public static boolean hasOriginalNameKey(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(originalNameKey);
    }

    public static boolean hasFilledWithKey(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(filledWithKey);
    }

    public static HashMap<ItemStat, Double> getAllStats(ItemStack item) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat itemStat : ItemStat.values()) {
            if (hasStat(item, itemStat)) {
                stats.put(itemStat, getStatValue(item, itemStat));
            }
        }

        return stats;
    }

    public static HashMap<String, Double> convertItemStatsToPlayerStats(ItemStack item) {
        return new HashMap<>(){{
            for (Entry<ItemStat, Double> statEntry : getAllStats(item).entrySet()) {
                put(ItemStat.toString(statEntry.getKey()).toLowerCase().replaceAll(" ", ""), statEntry.getValue());
            }
        }};
    }

    public static HashMap<ItemStat, Double> getAllDamageStats(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = new HashMap<>();

        if (!hasDamageStats(item)) return damageStats;

        for (ItemStat itemStat : ItemStat.values()) {
            if (hasStat(item, itemStat)) {
                damageStats.put(itemStat, getStatValue(item, itemStat));
            }
        }

        return damageStats;
    }

    public static HashMap<ItemStat, Double> multiplyAllDamageStats(ItemStack item, double multiplier) {
        HashMap<ItemStat, Double> multipliedDamage = getAllDamageStats(item);

        for (Map.Entry<ItemStat, Double> damageEntry : multipliedDamage.entrySet()) {
            damageEntry.setValue(damageEntry.getValue() * multiplier);
        }

        return multipliedDamage;
    }

    public static <T extends Number & Comparable<T>> LinkedHashMap<ItemStat, T> sortStats(HashMap<ItemStat, T> itemStats) {
        LinkedHashMap<ItemStat, T> sortedStats = new LinkedHashMap<>();
        List<Map.Entry<ItemStat, T>> entryList = new ArrayList<>(itemStats.entrySet());

        entryList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<ItemStat, T> entry : entryList) {
            sortedStats.put(entry.getKey(), entry.getValue());
        }

        return sortedStats;
    }

    public static ItemType getItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(itemTypeKey)) {
            return ItemType.fromString(pdc.get(itemTypeKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static ItemRarity getItemRarity(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(rarityKey, PersistentDataType.STRING)) {
            return ItemRarity.fromString(pdc.get(itemTypeKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static IngredientType getIngredientType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(ingredientKey)) {
            return IngredientType.fromString(pdc.get(ingredientKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static SeedType getSeedType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(seedKey)) {
            return SeedType.fromString(pdc.get(seedKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static CropType getCropType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(cropKey)) {
            return CropType.fromString(pdc.get(cropKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static GardenModifier getGardenModifierType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(gardenModifierKey)) {
            return GardenModifier.fromString(pdc.get(gardenModifierKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static FoodType getFoodType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(foodTypeKey)) {
            return FoodType.fromString(pdc.get(foodTypeKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static NamespacedKey getFilledWithKey() {
        return filledWithKey;
    }
}
