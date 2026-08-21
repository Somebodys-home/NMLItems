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
    private static NamespacedKey itemTypeKey = new NamespacedKey(nmlItems, "item_type");
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

    public static void setStat(ItemStack item, ItemStat stat, double amount) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(makeKeyForStat(stat), PersistentDataType.DOUBLE, amount);
        item.setItemMeta(meta);
    }

    public static void setStats(ItemStack itemStack, HashMap<ItemStat, Double> stats) {
        for (Map.Entry<ItemStat, Double> entry : stats.entrySet()) {
            setStat(itemStack, entry.getKey(), entry.getValue());
        }
    }

    public static void removeStat(ItemStack item, ItemStat stat) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(makeKeyForStat(stat));
        item.setItemMeta(meta);
    }

    public static void clearStats(ItemStack item) {
        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                removeStat(item, stat);
            }
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
                    ItemStat stat = entry.getKey();
                    double value = entry.getValue();
                    int valueInt = (int) value;

                    switch (stat) {
                        case CRITCHANCE, CRITDAMAGE -> addedLore.add(ItemStat.toChatColor(stat) + "+ " + valueInt + "% " +
                                ItemStat.toString(stat) + " " + ItemStat.toEmoji(stat));
                        default ->  addedLore.add(ItemStat.toChatColor(stat) + "+ " + valueInt + " " + ItemStat.toString(stat) + " " + ItemStat.toEmoji(stat));
                    }
                });

        originalLore.addAll(addedLore);
        meta.setLore(originalLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
    }

    // for regular items, not weapons and such
    public static void updateItemLoreWithStats(ItemStack itemStack) {
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

    public static void updateLoreWithStat(ItemStack item, ItemStat stat, int value) {
        ItemMeta meta = item.getItemMeta();
        List<String> addedLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        if (stat == ItemStat.CRITCHANCE || stat == ItemStat.CRITDAMAGE) {
            addedLore.add(ItemStat.toChatColor(stat) + "+ " + value + "% " + ItemStat.toString(stat) + " " + ItemStat.toEmoji(stat));
        } else {
            addedLore.add(ItemStat.toChatColor(stat) + "+ " + value + " " + ItemStat.toString(stat) + " " + ItemStat.toEmoji(stat));
        }

        meta.setLore(addedLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static void updateUnusableItemName(ItemStack item, boolean usable) {
        ItemMeta meta = item.getItemMeta();
        String originalName = getOriginalItemName(item);
        String editedName;

        if (!usable) {
            editedName = originalName.replaceAll("§[0-9a-fk-or]", "");
            editedName = "§c§m" + editedName;
        } else {
            editedName = originalName;
        }

        // Only update if the name is actually different
        if (editedName != null && !editedName.equals(meta.getDisplayName())) {
            meta.setDisplayName(editedName);
            item.setItemMeta(meta);
        }
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
        return switch (itemStat) {
            case CRITCHANCE, CRITDAMAGE -> ItemStat.toChatColor(itemStat) + "+ " + value + "% " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
            default ->  ItemStat.toChatColor(itemStat) + "+ " + value + " " + ItemStat.toString(itemStat) + " " + ItemStat.toEmoji(itemStat);
        };
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

    public static double calcCropStatValue(CropType cropType, int level, double stars) {
        return switch (cropType) {
            case WHEAT_BUNDLE -> Math.max(level / 2.0, 1);
            case SUGAR_CANE -> Math.max(level / 3.0, 1);
            case RHUBARB -> (int) Math.round(level * 1.5);
            default -> 1;
        } * MaterialStars.getStarMultiplier(MaterialStars.toMaterialStars(stars));
    }

    public static double getStatValue(ItemStack item, ItemStat stat) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (hasStat(item, stat)) {
            return pdc.get(makeKeyForStat(stat), PersistentDataType.DOUBLE);
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

    public static boolean hasStat(ItemStack item, ItemStat stat) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(makeKeyForStat(stat), PersistentDataType.DOUBLE);
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

    public static ItemStack[] getAllItemsInPie(ItemStack pie) {
        if (getIngredientType(pie) == IngredientType.FILLED_PIE_CRUST) {
            byte[] decodedItemsbtyes = Base64.getDecoder().decode(pie.getItemMeta().getPersistentDataContainer().get(getFilledWithKey(), PersistentDataType.STRING));

            return ItemStack.deserializeItemsFromBytes(decodedItemsbtyes);
        } else {
            return new ItemStack[0];
        }
    }

    public static HashMap<ItemStat, Double> getAllStats(ItemStack item) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                stats.put(stat, getStatValue(item, stat));
            }
        }

        return stats;
    }

    public static HashMap<String, Double> convertItemStatsToPlayerStats(ItemStack item) {
        HashMap<String, Double> playerStatMap = new HashMap<>();
        HashMap<ItemStat, Double> itemStatMap = getAllStats(item);

        for(Map.Entry<ItemStat, Double> statEntry : itemStatMap.entrySet()) {
            playerStatMap.put(ItemStat.toString(statEntry.getKey()).toLowerCase().replaceAll(" ", ""), statEntry.getValue());
        }

        return playerStatMap;
    }

    public static HashMap<ItemStat, Double> getAllDamageStats(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = new HashMap<>();

        if (!hasDamageStats(item)) return damageStats;

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                damageStats.put(stat, getStatValue(item, stat));
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

    private static NamespacedKey makeKeyForStat(ItemStat stat) {
        return new NamespacedKey(nmlItems, ItemStat.toString(stat).replaceAll(" ", ""));
    }

    public static NamespacedKey getItemTypeKey() {
        return itemTypeKey;
    }

    public static NamespacedKey getLevelKey() {
        return levelKey;
    }

    public static NamespacedKey getOriginalNameKey() {
        return originalNameKey;
    }

    public static NamespacedKey getSecondaryTypeKey() {
        return secondaryTypeKey;
    }

    public static NamespacedKey getRarityKey() {
        return rarityKey;
    }

    public static NamespacedKey getStarsKey() {
        return starsKey;
    }

    public static NamespacedKey getSeedKey() {
        return seedKey;
    }

    public static NamespacedKey getCropKey() {
        return cropKey;
    }

    public static NamespacedKey getGardenModifierKey() {
        return gardenModifierKey;
    }

    public static NamespacedKey getIngredientKey() {
        return ingredientKey;
    }

    public static NamespacedKey getFilledWithKey() {
        return filledWithKey;
    }

    public static NamespacedKey getServingsKey() {
        return servingsKey;
    }

    public static NamespacedKey getFoodTypeKey() {
        return foodTypeKey;
    }
}
