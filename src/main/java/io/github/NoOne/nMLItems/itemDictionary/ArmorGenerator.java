package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemRarity;
import io.github.NoOne.nMLItems.ItemStat;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static io.github.NoOne.nMLItems.ItemStat.*;

public class ArmorGenerator {
    public static ItemStack generateArmor(Player receiver, ItemRarity rarity, ItemType type, ItemType armorPiece, int level) {
        ItemStack armor = new ItemStack(ItemType.getItemTypeMaterial(type, armorPiece));
        ItemMeta meta = armor.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        List<String> lore = new ArrayList<>();

        pdc.set(ItemSystem.makeItemTypeKey(type), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemTypeKey(armorPiece), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemRarityKey(rarity), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        meta.setUnbreakable(true);
        armor.setItemMeta(meta);

        String name = NameGenerator.generateItemName(type, armorPiece, rarity);
        meta.setDisplayName(name);
        pdc.set(ItemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);

        lore.add("§o§fLv. " + level + "§r " +  ItemRarity.getItemRarityColor(rarity) + ChatColor.BOLD + ItemRarity.getItemRarityString(rarity).toUpperCase() + " " + ItemType.getItemTypeString(type).toUpperCase() + " " + ItemType.getItemTypeString(armorPiece).toUpperCase());
        lore.add("");
        meta.setLore(lore);
        armor.setItemMeta(meta);

        generateArmorStats(armor, type, rarity, level);
        ItemSystem.updateUnusableItemName(armor, ItemSystem.isItemUsable(armor, receiver));

        return armor;
    }

    public static void generateArmorStats(ItemStack armor, ItemType type, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstDefenseTypes = null;
        List<ItemStat> possibleSecondDefenseTypes = null;

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
        int secondDefense = level;

        switch (rarity) {
            case COMMON -> {
                ItemSystem.setStat(armor, firstStat, firstStatValue);
            }
            case UNCOMMON, RARE -> {
                if (firstStat == secondStat) {
                    ItemSystem.setStat(armor, firstStat, firstStatValue + secondDefense);
                } else {
                    ItemSystem.setStat(armor, firstStat, firstStatValue);
                    ItemSystem.setStat(armor, secondStat, secondDefense);
                }
            }
            case MYTHICAL -> {
                firstStatValue = level * 3;

                if (firstStat == secondStat) {
                    ItemSystem.setStat(armor, firstStat, firstStatValue + secondDefense);
                } else {
                    ItemSystem.setStat(armor, firstStat, firstStatValue);
                    ItemSystem.setStat(armor, secondStat, secondDefense);
                }
            }
        }

        ItemSystem.updateLoreWithStats(armor);
    }
}
