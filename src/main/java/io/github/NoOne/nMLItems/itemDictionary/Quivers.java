package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemRarity.COMMON;
import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class Quivers {
    public static ItemStack generateQuiver(Player receiver, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(QUIVER, null, rarity);
        ItemStack quiver = ItemCreator.createItem(
                ItemType.getItemTypeMaterial(QUIVER),
                name,
                List.of(
                        "§o§fLv. " + level + "§r " + ItemRarity.getItemRarityColor(rarity) + ChatColor.BOLD + ItemRarity.getItemRarityString(rarity).toUpperCase() +
                                " " + ItemType.getItemTypeString(QUIVER).toUpperCase(),
                        ""
                )
        );

        ItemMeta meta = quiver.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ItemSystem.makeItemTypeKey(QUIVER), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemRarityKey(rarity), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(ItemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        quiver.setItemMeta(meta);

        generateMainStats(quiver, rarity, level);
        generateSecondaryStats(quiver, rarity, level);
        ItemSystem.updateUnusableItemName(quiver, ItemSystem.isItemUsable(quiver, receiver));
        return quiver;
    }

    private static void generateMainStats(ItemStack weapon, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstStats = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE, CRITCHANCE, CRITDAMAGE));
        List<ItemStat> possibleSecondStats = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE, CRITCHANCE, CRITDAMAGE));

        ItemStat firstStat = possibleFirstStats.get(ThreadLocalRandom.current().nextInt(possibleFirstStats.size()));
        int firstStatValue = level * 2;
        ItemStat secondStat = possibleSecondStats.get(ThreadLocalRandom.current().nextInt(possibleSecondStats.size()));
        int secondStatValue = level;

        switch (rarity) {
            case COMMON -> {
                ItemSystem.setStat(weapon, firstStat, firstStatValue);
            }
            case UNCOMMON, RARE -> {
                if (firstStat == secondStat) {
                    ItemSystem.setStat(weapon, firstStat, firstStatValue + secondStatValue);
                } else {
                    ItemSystem.setStat(weapon, firstStat, firstStatValue);
                    ItemSystem.setStat(weapon, secondStat, secondStatValue);
                }
            }
            case MYTHICAL -> {
                firstStatValue = level * 3;

                if (firstStat == secondStat) {
                    ItemSystem.setStat(weapon, firstStat, firstStatValue + secondStatValue);
                } else {
                    ItemSystem.setStat(weapon, firstStat, firstStatValue);
                    ItemSystem.setStat(weapon, secondStat, secondStatValue);
                }
            }
        }

        ItemSystem.updateLoreWithStats(weapon);
    }

    private static void generateSecondaryStats(ItemStack quiver, ItemRarity rarity, int level) {
        HashMap<ItemStat, Integer> statMap = new HashMap<>();
        statMap.put(CRITCHANCE, level * 2);
        statMap.put(CRITDAMAGE, level * 10);

        // divider
        if (rarity != COMMON) {
            ItemMeta meta = quiver.getItemMeta();
            List<String> addedLore = meta.getLore();

            addedLore.add("§7─────────────");
            meta.setLore(addedLore);
            quiver.setItemMeta(meta);
        }

        // generate stat rolls
        List<Map.Entry<ItemStat, Integer>> statEntries = new ArrayList<>(statMap.entrySet());
        HashMap<ItemStat, Integer> selectedStats = new HashMap<>();
        int rolls = 0;

        switch (rarity) {
            case UNCOMMON -> rolls = 1;
            case RARE -> rolls = 2;
            case MYTHICAL -> rolls = 4;
        }

        for (int i = 0; i < rolls; i++) {
            Map.Entry<ItemStat, Integer> randomEntry = statEntries.get(new Random().nextInt(statEntries.size()));
            ItemStat randomItemStat = randomEntry.getKey();
            int randomStatValue = randomEntry.getValue();

            selectedStats.merge(randomItemStat, randomStatValue, Integer::sum);
        }

        // update stats
        for (Map.Entry<ItemStat, Integer> selectedStatEntry : selectedStats.entrySet()) {
            ItemSystem.setStat(quiver, selectedStatEntry.getKey(), selectedStatEntry.getValue());
            ItemSystem.updateLoreWithStat(quiver, selectedStatEntry.getKey(), selectedStatEntry.getValue());
        }
    }
}
