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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static io.github.NoOne.nMLItems.enums.ItemStat.*;

public class Armor {
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

    public static ItemStack generateArmor(Player receiver, ItemRarity rarity, ItemType type, ItemType armorPiece, int level) {
        String name = NameGenerator.generateItemName(type, armorPiece, rarity);
        String title = "§o§fLv. " + level + "§r " +  ItemRarity.getItemRarityColor(rarity) + ChatColor.BOLD +
                ItemRarity.getItemRarityString(rarity).toUpperCase() + " " + ItemType.getItemTypeString(type).toUpperCase() + " " +
                ItemType.getItemTypeString(armorPiece).toUpperCase();

        ItemStack armor = ItemCreator.createItem(
                ItemType.getItemTypeMaterial(type, armorPiece),
                name,
                List.of(title, "")
        );
        ItemMeta meta = armor.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.makeItemTypeKey(type), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.makeItemTypeKey(armorPiece), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.makeItemRarityKey(rarity), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        armor.setItemMeta(meta);

        generateArmorStats(armor, type, rarity, level);
        itemSystem.updateUnusableItemName(armor, itemSystem.isItemUsable(armor, receiver));
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
                itemSystem.setStat(armor, firstStat, firstStatValue);
            }
            case UNCOMMON, RARE -> {
                if (firstStat == secondStat) {
                    itemSystem.setStat(armor, firstStat, firstStatValue + secondDefense);
                } else {
                    itemSystem.setStat(armor, firstStat, firstStatValue);
                    itemSystem.setStat(armor, secondStat, secondDefense);
                }
            }
            case MYTHICAL -> {
                firstStatValue = level * 3;

                if (firstStat == secondStat) {
                    itemSystem.setStat(armor, firstStat, firstStatValue + secondDefense);
                } else {
                    itemSystem.setStat(armor, firstStat, firstStatValue);
                    itemSystem.setStat(armor, secondStat, secondDefense);
                }
            }
        }

        itemSystem.updateLoreWithStats(armor);
    }
}
