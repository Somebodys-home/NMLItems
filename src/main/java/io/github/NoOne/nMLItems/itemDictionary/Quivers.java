package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.QUIVER;

public class Quivers {
    public static ItemStack generateQuiver(Player receiver, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(QUIVER, null, rarity);
        ItemStack quiver = ItemCreator.createItem(
                ItemType.toMaterial(QUIVER),
                name,
                List.of(
                        "§o§fLv. " + level + "§r " + ItemRarity.toChatColor(rarity) + ChatColor.BOLD + ItemRarity.toString(rarity).toUpperCase() +
                                " " + ItemType.toString(QUIVER).toUpperCase(),
                        ""
                )
        );

        ItemMeta meta = quiver.getItemMeta();

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        quiver.setItemMeta(meta);

        ItemSystem.setItemType(quiver, QUIVER);
        ItemSystem.setRarity(quiver, rarity);
        ItemSystem.setLevel(quiver, level);
        ItemSystem.setOriginalName(quiver, name);

        generateMainStats(quiver, rarity, level);
        //generateSecondaryStats(quiver, rarity, level);
        ItemSystem.setUsability(quiver, true);
        ItemSystem.usableItemCheck(quiver, receiver);

        return quiver;
    }

    private static void generateMainStats(ItemStack quiver, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstStats = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE, CRITCHANCE, CRITDAMAGE));
        List<ItemStat> possibleSecondStats = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE, CRITCHANCE, CRITDAMAGE));
        ItemStat firstStat = possibleFirstStats.get(ThreadLocalRandom.current().nextInt(possibleFirstStats.size()));
        ItemStat secondStat = possibleSecondStats.get(ThreadLocalRandom.current().nextInt(possibleSecondStats.size()));
        int firstStatValue;

        if (rarity == ItemRarity.MYTHICAL) {
            firstStatValue = level * 3;
        } else {
            firstStatValue = level * 2;
        }

        HashMap<ItemStat, Integer> selectedStats = new HashMap<>(){{
            put(firstStat, firstStatValue);
            merge(secondStat, level, Integer::sum);
        }};

        ItemSystem.setStats(quiver, selectedStats);
        ItemSystem.updateLoreWithStats(quiver, ItemSystem.sortStats(selectedStats));
    }

//    private static void generateSecondaryStats(ItemStack quiver, ItemRarity rarity, int level) {
//        HashMap<ItemStat, Integer> statMap = new HashMap<>(){{
//            put(CRITCHANCE, level);
//            put(CRITDAMAGE, level * 5);
//        }};
//
//        if (rarity != COMMON) { // commons dont get extra stat rolls
//            HashMap<ItemStat, Integer> selectedStats = new HashMap<>();
//            int rolls = switch (rarity) {
//                case UNCOMMON -> 1;
//                case RARE -> 2;
//                case MYTHICAL -> 4;
//                default -> 0;
//            };
//
//            ItemSystem.addLoreToItem(quiver, List.of("§7─────────────")); // divider
//
//            for (int i = 0; i < rolls; i++) {
//                Map.Entry<ItemStat, Integer> randomEntry = statMap.entrySet().stream().toList().get(new Random().nextInt(statMap.size()));
//                ItemStat randomItemStat = randomEntry.getKey();
//                int randomStatValue = randomEntry.getValue();
//
//                selectedStats.merge(randomItemStat, randomStatValue, Integer::sum);
//            }
//
//            ItemSystem.setStats(quiver, selectedStats);
//            ItemSystem.updateLoreWithStats(quiver, ItemSystem.sortStats(selectedStats));
//        }
//    }
}
