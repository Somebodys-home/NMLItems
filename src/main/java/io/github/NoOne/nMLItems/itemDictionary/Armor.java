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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;

public class Armor {
    public static ItemStack generateArmor(Player receiver, ItemRarity rarity, ItemType weight, ItemType type, int level) {
        String name = NameGenerator.generateItemName(weight, type, rarity);
        String title = "§o§fLv. " + level + "§r" +  ItemRarity.toChatColor(rarity) + "§l " + ItemRarity.toString(rarity).toUpperCase() + " " +
                ItemType.toString(weight).toUpperCase() + " " + ItemType.toString(type).toUpperCase();

        ItemStack armor = ItemCreator.createItem(
                ItemType.toMaterial(weight, type),
                name,
                List.of(title, "")
        );
        ItemMeta meta = armor.getItemMeta();

        meta.setUnbreakable(true);
        armor.setItemMeta(meta);

        armor.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        ItemSystem.setItemType(armor, type);
        ItemSystem.setSecondaryType(armor, weight);
        ItemSystem.setRarity(armor, rarity);
        ItemSystem.setLevel(armor, level);
        ItemSystem.setOriginalName(armor, name);
        generateArmorStats(armor, weight, rarity, level);
        ItemSystem.updateUnusableItemName(armor, ItemSystem.isItemUsable(armor, receiver));

        return armor;
    }

    public static void generateArmorStats(ItemStack armor, ItemType type, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstDefenseTypes = null;
        List<ItemStat> possibleSecondDefenseTypes = null;
        HashMap<ItemStat, Integer> itemStats = new HashMap<>();

        switch (type) {
            case LIGHT -> {
                possibleFirstDefenseTypes = new ArrayList<>(List.of(OVERHEALTH));
                possibleSecondDefenseTypes = new ArrayList<>(List.of(PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST, AIRRESIST, RADIANTRESIST, NECROTICRESIST));
            }
            case MEDIUM -> {
                possibleFirstDefenseTypes = new ArrayList<>(List.of(EVASION));
                possibleSecondDefenseTypes = new ArrayList<>(List.of(PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST, AIRRESIST, RADIANTRESIST, NECROTICRESIST));
            }
            case HEAVY -> {
                possibleFirstDefenseTypes = new ArrayList<>(List.of(DEFENSE));
                possibleSecondDefenseTypes = new ArrayList<>(List.of(PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST, AIRRESIST, RADIANTRESIST, NECROTICRESIST));
            }
        }

        ItemStat firstStat = possibleFirstDefenseTypes.get(ThreadLocalRandom.current().nextInt(possibleFirstDefenseTypes.size()));
        int firstStatValue = level * 2;
        ItemStat secondStat = possibleSecondDefenseTypes.get(ThreadLocalRandom.current().nextInt(possibleSecondDefenseTypes.size()));
        int secondStatValue = level;

        switch (rarity) {
            case COMMON -> itemStats.put(firstStat, firstStatValue);
            case UNCOMMON, RARE -> {
                if (firstStat == secondStat) {
                    itemStats.put(firstStat, firstStatValue + secondStatValue);
                } else {
                    itemStats.put(firstStat, firstStatValue);
                    itemStats.put(secondStat, secondStatValue);
                }
            }
            case MYTHICAL -> {
                firstStatValue = level * 3;

                if (firstStat == secondStat) {
                    itemStats.put(firstStat, firstStatValue + secondStatValue);
                } else {
                    itemStats.put(firstStat, firstStatValue);
                    itemStats.put(secondStat, secondStatValue);
                }
            }
        }

        ItemSystem.setStats(armor, itemStats);
        ItemSystem.updateEquipmentLoreWithStats(armor);
    }
}
