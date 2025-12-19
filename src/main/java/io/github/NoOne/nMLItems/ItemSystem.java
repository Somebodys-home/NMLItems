package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.NoOne.nMLItems.ItemType.*;

public class ItemSystem {
    private static NMLItems nmlItems;
    private static SkillSetManager skillSetManager;
    private static NamespacedKey originalNameKey;
    private static NamespacedKey levelKey;
    private static NamespacedKey starsKey;
    private static NamespacedKey seedKey;
    private static NamespacedKey cropKey;

    public ItemSystem(NMLItems nmlItems) {
        this.nmlItems = nmlItems;
        skillSetManager = nmlItems.getSkillSetManager();
        originalNameKey = new NamespacedKey(nmlItems, "original_name");
        levelKey = new NamespacedKey(nmlItems, "level");
        starsKey = new NamespacedKey(nmlItems, "stars");
        seedKey = new NamespacedKey(nmlItems, "seed");
        cropKey = new NamespacedKey(nmlItems, "crop");
    }

    public static void setStat(ItemStack item, ItemStat stat, double amount) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(makeKeyForStat(stat), PersistentDataType.DOUBLE, amount);
        item.setItemMeta(meta);
    }

    public static void setStats(ItemStack item, HashMap<ItemStat, Double> statMap) {
        for (Map.Entry<ItemStat, Double> statEntry : statMap.entrySet()) {
            setStat(item, statEntry.getKey(), statEntry.getValue());
        }
    }

    public static void removeStat(ItemStack item, ItemStat stat) {
        if (!item.hasItemMeta()) return;

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

    public static boolean hasStat(ItemStack item, ItemStat stat) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(makeKeyForStat(stat), PersistentDataType.DOUBLE);
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

    public static HashMap<ItemStat, Double> getAllStats(ItemStack item) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                stats.put(stat, getStatValue(item, stat));
            }
        }
        return stats;
    }

    public static ItemType getItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemType type : ItemType.values()) {
            if (pdc.has(makeItemTypeKey(type), PersistentDataType.INTEGER)) return type;
        }

        return null;
    }

    public static ItemRarity getItemRarity(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemRarity rarity : ItemRarity.values()) {
            if (pdc.has(makeItemRarityKey(rarity), PersistentDataType.INTEGER)) return rarity;
        }

        return null;
    }

    public static void updateLoreWithStats(ItemStack item) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> originalLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> addedLore = new ArrayList<>();
        HashMap<ItemStat, Double> itemStats = getAllStats(item);

        itemStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Descending sort
                .forEachOrdered(entry -> {
                    double value = entry.getValue();
                    int valueInt = (int) value;
                    addedLore.add(ItemStat.getStatColor(entry.getKey()) + "+ " + valueInt + " " + ItemStat.getStatString(entry.getKey()) + " " + ItemStat.getStatEmoji(entry.getKey()));
                });

        originalLore.addAll(addedLore);
        meta.setLore(originalLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static void updateLoreWithStat(ItemStack item, ItemStat stat, int value) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> addedLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        addedLore.add(ItemStat.getStatColor(stat) + "+ " + value + " " + ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
        meta.setLore(addedLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
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

    public static double getTotalDamageOfItem(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = getAllDamageStats(item);
        double totalDamage = 0;

        for (Map.Entry<ItemStat, Double> damageEntry : damageStats.entrySet()) {
            totalDamage += damageEntry.getValue();
        }

        return totalDamage;
    }

    public static String getOriginalItemName(ItemStack item) {
        if (item == null || item.getType().isAir()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(originalNameKey, PersistentDataType.STRING)) return null;

        return pdc.get(originalNameKey, PersistentDataType.STRING);
    }

    public static void updateUnusableItemName(ItemStack item, boolean usable) {
        if (item == null || !item.hasItemMeta()) return;

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

    public static HashMap<String, Double> convertItemStatsToPlayerStats(ItemStack item) {
        HashMap<String, Double> playerStatMap = new HashMap<>();
        HashMap<ItemStat, Double> itemStatMap = getAllStats(item);

        for(Map.Entry<ItemStat, Double> statEntry : itemStatMap.entrySet()) {
           playerStatMap.put(ItemStat.getStatString(statEntry.getKey()).toLowerCase().replaceAll(" ", ""), statEntry.getValue());
        }

        return playerStatMap;
    }

    public static SeedType getSeedType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(seedKey)) {
            return SeedType.getSeedType(pdc.get(seedKey, PersistentDataType.STRING));
        }

        return null;
    }

    public static int getLevel(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(levelKey)) {
            return pdc.get(levelKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public static double getStars(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(starsKey)) {
            return pdc.get(starsKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public static boolean isItemUsable(ItemStack item, Player player) {
        if (item == null || !item.hasItemMeta()) return false;

        Integer itemLevel = item.getItemMeta().getPersistentDataContainer().get(levelKey, PersistentDataType.INTEGER);

        if (itemLevel == null) return false;

        int combatLevel = skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getCombatLevel();
        return combatLevel >= itemLevel;
    }

    public static boolean isHoeUsable(ItemStack item, Player player) {
        if (item == null || !item.hasItemMeta()) return false;

        Integer itemLevel = item.getItemMeta().getPersistentDataContainer().get(levelKey, PersistentDataType.INTEGER);

        if (itemLevel == null) return false;

        int farmingLevel = skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getFarmingLevel();
        return farmingLevel >= itemLevel;
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
        return getItemType(item) == HELMET ||
                getItemType(item) == CHESTPLATE ||
                getItemType(item) == LEGGINGS ||
                getItemType(item) == BOOTS ||
                getItemType(item) == SHIELD ||
                getItemType(item) == QUIVER;
    }

    public static boolean isWeapon(ItemStack item) {
        return getItemType(item) == SWORD ||
                getItemType(item) == DAGGER ||
                getItemType(item) == AXE ||
                getItemType(item) == HAMMER ||
                getItemType(item) == SPEAR ||
                getItemType(item) == GLOVE ||
                getItemType(item) == BOW ||
                getItemType(item) == WAND ||
                getItemType(item) == STAFF ||
                getItemType(item) == CATALYST;
    }

    private static NamespacedKey makeKeyForStat(ItemStat stat) {
        return new NamespacedKey(nmlItems, ItemStat.getStatString(stat).replaceAll(" ", ""));
    }

    public static NamespacedKey makeItemTypeKey(ItemType type) {
        return new NamespacedKey(nmlItems, ItemType.getItemTypeString(type));
    }

    public static NamespacedKey makeItemRarityKey(ItemRarity rarity) {
        return new NamespacedKey(nmlItems, ItemRarity.getItemRarityString(rarity));
    }

    public static NamespacedKey getLevelKey() {
        return levelKey;
    }

    public static NamespacedKey getOriginalNameKey() {
        return originalNameKey;
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
}
