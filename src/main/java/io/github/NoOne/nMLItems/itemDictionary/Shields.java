package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.SHIELD;

public class Shields {
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

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
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(SHIELD));
        pdc.set(itemSystem.getRarityKey(), PersistentDataType.STRING, ItemRarity.toString(rarity));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        shield.setItemMeta(meta);

        generateShieldStats(shield, rarity, level);
        itemSystem.updateUnusableItemName(shield, itemSystem.isItemUsable(shield, receiver));
        return shield;
    }

    public static void generateShieldStats(ItemStack shield, ItemRarity rarity, int level) {
        List<ItemStat> possibleDefenseStats = new ArrayList<>(List.of(GUARD, DEFENSE, OVERHEALTH, PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST,
                                                                    AIRRESIST, RADIANTRESIST, NECROTICRESIST));
        int firstDefenseValue = (level * 5) + 10;
        ItemStat secondType = possibleDefenseStats.get(ThreadLocalRandom.current().nextInt(possibleDefenseStats.size()));
        int secondDefenseValue = level;

        switch (rarity) {
            case COMMON -> {
                itemSystem.setStat(shield, GUARD, firstDefenseValue);
            }
            case UNCOMMON, RARE -> {
                if (secondType == GUARD) {
                    itemSystem.setStat(shield, GUARD, firstDefenseValue + secondDefenseValue);
                } else {
                    itemSystem.setStat(shield, GUARD, firstDefenseValue);
                    itemSystem.setStat(shield, secondType, secondDefenseValue);
                }
            }
            case MYTHICAL -> {
                firstDefenseValue = (level * 8) + 10;

                if (secondType == GUARD) {
                    itemSystem.setStat(shield, GUARD, firstDefenseValue + secondDefenseValue);
                } else {
                    itemSystem.setStat(shield, GUARD, firstDefenseValue);
                    itemSystem.setStat(shield, secondType, secondDefenseValue);
                }
            }
        }

        itemSystem.updateEquipmentLoreWithStats(shield);
    }
}
