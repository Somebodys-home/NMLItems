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

        generateStats(quiver, rarity, level);
        ItemSystem.setUsability(quiver, true);
        ItemSystem.usableItemCheck(quiver, receiver);

        return quiver;
    }

    private static void generateStats(ItemStack quiver, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstStats = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE, CRITCHANCE, CRITDAMAGE));
        ItemStat firstStat = possibleFirstStats.get(ThreadLocalRandom.current().nextInt(possibleFirstStats.size()));
        ItemStat secondStat = possibleFirstStats.get(ThreadLocalRandom.current().nextInt(possibleFirstStats.size()));
        int firstStatValue = (int) switch (rarity) {
            case UNCOMMON -> Math.round(level * 1.2);
            case MYTHICAL -> Math.round(level * 1.5);
            default -> level;
        };

        HashMap<ItemStat, Integer> selectedStats = new HashMap<>(){{
            put(firstStat, firstStatValue);

            if (rarity == ItemRarity.RARE || rarity == ItemRarity.MYTHICAL) {
                merge(secondStat, Math.max(1, firstStatValue / 2), Integer::sum);
            }
        }};

        ItemSystem.setStats(quiver, selectedStats);
        ItemSystem.updateLoreWithStats(quiver, ItemSystem.sortStats(selectedStats));
    }
}
