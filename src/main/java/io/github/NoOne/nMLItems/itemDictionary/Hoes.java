package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.NMLItems;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;

public class Hoes {
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

    public static ItemStack generateHoe(Player receiver, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(ItemType.HOE, null, rarity);
        ItemStack hoe = ItemCreator.createItem(
                ItemType.getItemTypeMaterial(ItemType.HOE),
                name,
                List.of(
                        "§o§fLv. " + level + "§r " +  ItemRarity.getItemRarityColor(rarity) + ChatColor.BOLD + ItemRarity.getItemRarityString(rarity).toUpperCase() + " " +
                                ItemType.getItemTypeString(ItemType.HOE).toUpperCase(),
                        ""
                )
        );

        ItemMeta meta = hoe.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.makeItemTypeKey(ItemType.HOE), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.makeItemRarityKey(rarity), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(meta);

        generateHoeStats(hoe, rarity, level);
        itemSystem.updateUnusableItemName(hoe, itemSystem.isItemUsable(hoe, receiver));
        return hoe;
    }

    public static void generateHoeStats(ItemStack hoe, ItemRarity rarity, int level) {
        List<ItemStat> possibleStats = new ArrayList<>(List.of(HARVEST, YIELD));

        if (rarity == ItemRarity.MYTHICAL) {
            possibleStats.add(ACRE);
        }

        ItemStat firstStat = YIELD;
        ItemStat secondStat = possibleStats.get(ThreadLocalRandom.current().nextInt(possibleStats.size()));
        ItemStat thirdStat = possibleStats.get(ThreadLocalRandom.current().nextInt(possibleStats.size()));
        int firstStatValue = (int) (level * 1.5);
        int secondStatValue = 0;
        int thirdStatValue = 0;

        if (rarity == ItemRarity.MYTHICAL) firstStatValue *= 2;

        switch (secondStat) {
            case HARVEST -> secondStatValue = (level / 3) + 1;
            case YIELD -> secondStatValue = (level / 2) + 1;
            case ACRE -> secondStatValue = (level / 10) + 1;
        }

        switch (thirdStat) {
            case HARVEST -> thirdStatValue = (level / 3) + 1;
            case YIELD -> thirdStatValue = (level / 2) + 1;
            case ACRE -> thirdStatValue = (level / 10) + 1;
        }


        int finalFirstStatValue = firstStatValue;
        int finalSecondStatValue = secondStatValue;
        int finalThirdStatValue = thirdStatValue;

        switch (rarity) {
            case COMMON -> itemSystem.setStat(hoe, firstStat, firstStatValue);
            case UNCOMMON, RARE -> {
                if (firstStat == secondStat) {
                    itemSystem.setStat(hoe, firstStat, firstStatValue + secondStatValue);
                } else {
                    itemSystem.setStat(hoe, firstStat, firstStatValue);
                    itemSystem.setStat(hoe, secondStat, secondStatValue);
                }
            }
            case MYTHICAL -> {
                HashMap<ItemStat, Integer> finalMap = new HashMap<>();
                HashMap<ItemStat, Integer> finalStatMap = new HashMap<>() {{
                   put(firstStat, finalFirstStatValue);
                }};
                HashMap<ItemStat, Integer> secondStatMap = new HashMap<>() {{
                    put(secondStat, finalSecondStatValue);
                }};
                HashMap<ItemStat, Integer> thirdStatMap = new HashMap<>() {{
                    put(thirdStat, finalThirdStatValue);
                }};
                List.of(finalStatMap, secondStatMap, thirdStatMap)
                        .forEach(m -> m.forEach((k,v) -> finalMap.merge(k, v, Integer::sum)));

                for (Map.Entry<ItemStat, Integer> entry : finalMap.entrySet()) {
                    itemSystem.setStat(hoe, entry.getKey(), entry.getValue());
                }
            }
        }

        itemSystem.updateLoreWithStats(hoe);
    }
}
