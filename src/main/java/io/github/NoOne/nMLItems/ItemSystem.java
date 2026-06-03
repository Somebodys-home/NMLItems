package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import io.github.NoOne.nMLItems.enums.SeedType;
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

import io.github.NoOne.nMLItems.enums.ItemType.*;

import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class ItemSystem {
    private NMLItems nmlItems;
    private SkillSetManager skillSetManager;
    private NamespacedKey originalNameKey;
    private NamespacedKey levelKey;
    private NamespacedKey starsKey;
    private NamespacedKey seedKey;
    private NamespacedKey cropKey;
    private NamespacedKey ingredientKey;

    public ItemSystem(NMLItems nmlItems) {
        this.nmlItems = nmlItems;
        skillSetManager = nmlItems.getSkillSetManager();
        originalNameKey = new NamespacedKey(nmlItems, "original_name");
        levelKey = new NamespacedKey(nmlItems, "level");
        starsKey = new NamespacedKey(nmlItems, "stars");
        seedKey = new NamespacedKey(nmlItems, "seed");
        cropKey = new NamespacedKey(nmlItems, "crop");
        ingredientKey = new NamespacedKey(nmlItems, "ingredient");
    }

    public void setStat(ItemStack item, ItemStat stat, double amount) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(makeKeyForStat(stat), PersistentDataType.DOUBLE, amount);
        item.setItemMeta(meta);
    }

    public void removeStat(ItemStack item, ItemStat stat) {

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(makeKeyForStat(stat));
        item.setItemMeta(meta);
    }

    public void clearStats(ItemStack item) {
        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                removeStat(item, stat);
            }
        }
    }

    public boolean hasStat(ItemStack item, ItemStat stat) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(makeKeyForStat(stat), PersistentDataType.DOUBLE);
    }

    public double getStatValue(ItemStack item, ItemStat stat) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (hasStat(item, stat)) {
            return pdc.get(makeKeyForStat(stat), PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public HashMap<ItemStat, Double> getAllStats(ItemStack item) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                stats.put(stat, getStatValue(item, stat));
            }
        }
        return stats;
    }

    public ItemType getItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemType type : ItemType.values()) {
            if (pdc.has(makeItemTypeKey(type), PersistentDataType.INTEGER)) return type;
        }

        return null;
    }

    public ItemRarity getItemRarity(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemRarity rarity : ItemRarity.values()) {
            if (pdc.has(makeItemRarityKey(rarity), PersistentDataType.INTEGER)) return rarity;
        }

        return null;
    }

    public void updateLoreWithStats(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> originalLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> addedLore = new ArrayList<>();
        HashMap<ItemStat, Double> itemStats = getAllStats(item);

        itemStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Descending sort
                .forEachOrdered(entry -> {
                    ItemStat stat = entry.getKey();
                    double value = entry.getValue();
                    int valueInt = (int) value;

                    switch (stat) {
                        case CRITCHANCE, CRITDAMAGE -> addedLore.add(ItemStat.getStatColor(stat) + "+ " + valueInt + "% " +
                                                                    ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
                        default ->  addedLore.add(ItemStat.getStatColor(stat) + "+ " + valueInt + " " + ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
                    }
                });

        originalLore.addAll(addedLore);
        meta.setLore(originalLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public void updateLoreWithStat(ItemStack item, ItemStat stat, int value) {
        ItemMeta meta = item.getItemMeta();
        List<String> addedLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        if (stat == ItemStat.CRITCHANCE || stat == ItemStat.CRITDAMAGE) {
            addedLore.add(ItemStat.getStatColor(stat) + "+ " + value + "% " + ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
        } else {
            addedLore.add(ItemStat.getStatColor(stat) + "+ " + value + " " + ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
        }

        meta.setLore(addedLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public HashMap<ItemStat, Double> getAllDamageStats(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = new HashMap<>();

        if (!hasDamageStats(item)) return damageStats;

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                damageStats.put(stat, getStatValue(item, stat));
            }
        }

        return damageStats;
    }

    public HashMap<ItemStat, Double> multiplyAllDamageStats(ItemStack item, double multiplier) {
        HashMap<ItemStat, Double> multipliedDamage = getAllDamageStats(item);

        for (Map.Entry<ItemStat, Double> damageEntry : multipliedDamage.entrySet()) {
            damageEntry.setValue(damageEntry.getValue() * multiplier);
        }

        return multipliedDamage;
    }

    public double getTotalDamageOfItem(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = getAllDamageStats(item);
        double totalDamage = 0;

        for (Map.Entry<ItemStat, Double> damageEntry : damageStats.entrySet()) {
            totalDamage += damageEntry.getValue();
        }

        return totalDamage;
    }

    public String getOriginalItemName(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(originalNameKey, PersistentDataType.STRING)) {
            return null;
        }

        return pdc.get(originalNameKey, PersistentDataType.STRING);
    }

    public void updateUnusableItemName(ItemStack item, boolean usable) {
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

    public HashMap<String, Double> convertItemStatsToPlayerStats(ItemStack item) {
        HashMap<String, Double> playerStatMap = new HashMap<>();
        HashMap<ItemStat, Double> itemStatMap = getAllStats(item);

        for(Map.Entry<ItemStat, Double> statEntry : itemStatMap.entrySet()) {
           playerStatMap.put(ItemStat.getStatString(statEntry.getKey()).toLowerCase().replaceAll(" ", ""), statEntry.getValue());
        }

        return playerStatMap;
    }

    public SeedType getSeedType(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(seedKey)) {
            return SeedType.getSeedType(pdc.get(seedKey, PersistentDataType.STRING));
        }

        return null;
    }

    public int getLevel(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(levelKey)) {
            return pdc.get(levelKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public double getStars(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(starsKey)) {
            return pdc.get(starsKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public boolean isItemUsable(ItemStack item, Player player) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        Integer itemLevel = item.getItemMeta().getPersistentDataContainer().get(levelKey, PersistentDataType.INTEGER);

        if (itemLevel == null) {
            return false;
        }

        switch (getItemType(item)) {
            case HOE: return skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getFarmingLevel() >= itemLevel;
            default: return skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getCombatLevel() >= itemLevel;
        }
    }

    public boolean isItemType(ItemStack itemStack, ItemType itemType) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(makeItemTypeKey(itemType));
    }

    public boolean hasDamageStats(ItemStack item) {
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

    public boolean isEquippable(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        return isItemType(item, HELMET) ||
                isItemType(item, CHESTPLATE) ||
                isItemType(item, LEGGINGS) ||
                isItemType(item, BOOTS) ||
                isItemType(item, SHIELD) ||
                isItemType(item, QUIVER);
    }

    public boolean isWeapon(ItemStack item) {
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

    public boolean hasLevelKey(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(levelKey);
    }

    private NamespacedKey makeKeyForStat(ItemStat stat) {
        return new NamespacedKey(nmlItems, ItemStat.getStatString(stat).replaceAll(" ", ""));
    }

    public NamespacedKey makeItemTypeKey(ItemType type) {
        return new NamespacedKey(nmlItems, ItemType.getItemTypeString(type));
    }

    public NamespacedKey makeItemRarityKey(ItemRarity rarity) {
        return new NamespacedKey(nmlItems, ItemRarity.getItemRarityString(rarity));
    }
    
    public NamespacedKey getLevelKey() {
        return levelKey;
    }

    public NamespacedKey getOriginalNameKey() {
        return originalNameKey;
    }

    public NamespacedKey getStarsKey() {
        return starsKey;
    }

    public NamespacedKey getSeedKey() {
        return seedKey;
    }

    public NamespacedKey getCropKey() {
        return cropKey;
    }

    public NamespacedKey getIngredientKey() {
        return ingredientKey;
    }
}
