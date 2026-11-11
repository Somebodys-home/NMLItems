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

public class Shields {
    public static ItemStack generateShield(Player receiver, ItemRarity rarity, int level) {
        ItemType shieldType = ItemType.SHIELD;
        String name = NameGenerator.generateItemName(shieldType, null, rarity);
        ItemStack shield = ItemCreator.createItem(
                shieldType.toMaterial(),
                name,
                List.of(
                        "§o§fLv. " + level + "§r " + rarity.toChatColor() + ChatColor.BOLD + rarity.toString().toUpperCase() +
                                " " + shieldType.toString().toUpperCase(),
                        ""
                )

        );

        ItemMeta meta = shield.getItemMeta();

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        shield.setItemMeta(meta);

        ItemSystem.setItemType(shield, shieldType);
        ItemSystem.setRarity(shield, rarity);
        ItemSystem.setLevel(shield, level);
        ItemSystem.setOriginalName(shield, name);
        generateShieldStats(shield, rarity, level);
        ItemSystem.setUsability(shield, true);
        ItemSystem.usableItemCheck(shield, receiver);

        return shield;
    }

    public static void generateShieldStats(ItemStack shield, ItemRarity rarity, int level) {
        List<ItemStat> possibleDefenseStats = new ArrayList<>(List.of(GUARD, DEFENSE, OVERHEALTH, PHYSICAL_RESIST, FIRE_RESIST, COLD_RESIST, EARTH_RESIST, LIGHTNING_RESIST,
                AIR_RESIST, RADIANT_RESIST, NECROTIC_RESIST));
        HashMap<ItemStat, Integer> selectedStats = new HashMap<>(){{
            int value = switch (rarity) {
                case MYTHICAL -> (level * 8) + 10;
                default -> (level * 5) + 10;
            };

            put(GUARD, value); // first stat will always be guard

            if (rarity != ItemRarity.COMMON) {
                ItemStat secondType = possibleDefenseStats.get(ThreadLocalRandom.current().nextInt(possibleDefenseStats.size()));

                // second stat will be random from the possible defense stats, combining if its guard
                merge(secondType, level, Integer::sum);
            }
        }};

        ItemSystem.setStats(shield, selectedStats);
        ItemSystem.updateLoreWithStats(shield, ItemSystem.sortStats(selectedStats));
    }
}
