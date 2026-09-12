package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;

public class Hoes {
    public static ItemStack generateHoe(Player receiver, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(ItemType.HOE, null, rarity);
        ItemStack hoe = ItemCreator.createItem(
                ItemType.toMaterial(ItemType.HOE),
                name,
                List.of(
                        "§o§fLv. " + level + "§r " +  ItemRarity.toChatColor(rarity) + "§l" + ItemRarity.toString(rarity).toUpperCase() + " " +
                                ItemType.toString(ItemType.HOE).toUpperCase(),
                        ""
                )
        );

        ItemMeta meta = hoe.getItemMeta();

        meta.setUnbreakable(true);
        hoe.setItemMeta(meta);

        hoe.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        ItemSystem.setItemType(hoe, ItemType.HOE);
        ItemSystem.setRarity(hoe, rarity);
        ItemSystem.setLevel(hoe, level);
        ItemSystem.setOriginalName(hoe, name);
        generateHoeStats(hoe, rarity, level);
        ItemSystem.updateUnusableItemName(hoe, ItemSystem.isItemUsable(hoe, receiver));
        return hoe;
    }

    public static void generateHoeStats(ItemStack hoe, ItemRarity rarity, int level) {
        HashMap<ItemStat, Integer> possibleStats = new HashMap<>(){{ // the value is the equation for that stat
            put(YIELD, (int) Math.max(1, level * .75));
            put(HARVEST, (int) Math.max(1, level * .35));

            if (rarity == ItemRarity.MYTHICAL) { // only mythical hoes can roll for the acre stat
                put(ACRE, (int) Math.max(1, level * .15));
            }
        }};

        HashMap<ItemStat, Integer> selectedStats = new HashMap<>(){{ // the first stat will always be yield
            put(YIELD, Math.toIntExact(Math.round(level * 1.5)));
        }};

        if (rarity != ItemRarity.COMMON) { // randomizing extra stats for anything above common
            int rolls = switch (rarity) {
                case UNCOMMON -> 2;
                case RARE -> 3;
                case MYTHICAL -> 4;
                default -> 0;
            };

            for (int i = 1; i < rolls; i++) {
                ItemStat stat = possibleStats.keySet().stream().toList().get(ThreadLocalRandom.current().nextInt(possibleStats.size()));
                int value = possibleStats.get(stat);

                if (selectedStats.containsKey(stat)) {
                    value = Math.toIntExact(Math.round(value * 1.3));
                }

                selectedStats.put(stat, value);
            }
        }

        ItemSystem.setStats(hoe, selectedStats);
        ItemSystem.updateLoreWithStats(hoe, ItemSystem.sortStats(selectedStats));
    }
}
