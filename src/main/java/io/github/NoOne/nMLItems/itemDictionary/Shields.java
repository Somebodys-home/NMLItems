package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.SHIELD;

public class Shields {
    public static ItemStack generateShield(Player receiver, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(SHIELD, null, rarity);
        ItemStack shield = ItemCreator.createItem(
                Material.SHIELD,
                name,
                List.of(
                        "§o§fLv. " + level + "§r " + ItemRarity.toChatColor(rarity) + ChatColor.BOLD + ItemRarity.toString(rarity).toUpperCase() +
                                " " + ItemType.toString(SHIELD).toUpperCase(),
                        ""
                )

        );

        ItemMeta meta = shield.getItemMeta();

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        shield.setItemMeta(meta);

        ItemSystem.setItemType(shield, SHIELD);
        ItemSystem.setRarity(shield, rarity);
        ItemSystem.setLevel(shield, level);
        ItemSystem.setOriginalName(shield, name);
        generateShieldStats(shield, rarity, level);
        ItemSystem.setUsability(shield, true);
        ItemSystem.usableItemCheck(shield, receiver);

        return shield;
    }

    public static void generateShieldStats(ItemStack shield, ItemRarity rarity, int level) {
        List<ItemStat> possibleDefenseStats = new ArrayList<>(List.of(GUARD, DEFENSE, OVERHEALTH, PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST,
                                                                    AIRRESIST, RADIANTRESIST, NECROTICRESIST));
        HashMap<ItemStat, Integer> selectedStats = new HashMap<>(){{
            int value = switch (rarity) {
                case MYTHICAL -> (level * 8) + 10;
                default -> (level * 5) + 10;
            };
            ItemStat secondType = possibleDefenseStats.get(ThreadLocalRandom.current().nextInt(possibleDefenseStats.size()));

            put(GUARD, value); // first stat will always be guard
            merge(secondType, level, Integer::sum); // second stat will be random from the possible defense stats, combining if its guard
        }};

        ItemSystem.setStats(shield, selectedStats);
        ItemSystem.updateLoreWithStats(shield, ItemSystem.sortStats(selectedStats));
    }
}
