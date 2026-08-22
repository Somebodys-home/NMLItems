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
import static io.github.NoOne.nMLItems.enums.ItemType.QUIVER;
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
        shield.setItemMeta(meta);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        ItemSystem.setItemType(shield, SHIELD);
        ItemSystem.setRarity(shield, rarity);
        ItemSystem.setLevel(shield, level);
        ItemSystem.setOriginalName(shield, name);

        generateShieldStats(shield, rarity, level);
        ItemSystem.updateUnusableItemName(shield, ItemSystem.isItemUsable(shield, receiver));
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
                ItemSystem.setStat(shield, GUARD, firstDefenseValue);
            }
            case UNCOMMON, RARE -> {
                if (secondType == GUARD) {
                    ItemSystem.setStat(shield, GUARD, firstDefenseValue + secondDefenseValue);
                } else {
                    ItemSystem.setStat(shield, GUARD, firstDefenseValue);
                    ItemSystem.setStat(shield, secondType, secondDefenseValue);
                }
            }
            case MYTHICAL -> {
                firstDefenseValue = (level * 8) + 10;

                if (secondType == GUARD) {
                    ItemSystem.setStat(shield, GUARD, firstDefenseValue + secondDefenseValue);
                } else {
                    ItemSystem.setStat(shield, GUARD, firstDefenseValue);
                    ItemSystem.setStat(shield, secondType, secondDefenseValue);
                }
            }
        }

        ItemSystem.updateEquipmentLoreWithStats(shield);
    }
}
