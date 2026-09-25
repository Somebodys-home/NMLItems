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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;

public class Armor {
    public static ItemStack generateArmor(Player receiver, ItemRarity rarity, ItemType weight, ItemType type, int level) {
        String name = NameGenerator.generateItemName(weight, type, rarity);
        String typeString = switch (weight) {
            case LIGHT -> switch (type) {
                case HELMET -> "HAT";
                case CHESTPLATE -> "TUNIC";
                case LEGGINGS -> "PANTS";
                case BOOTS -> "SHOES";
                default -> "";
            };
            case MEDIUM -> switch (type) {
                case HELMET -> "COIF";
                case CHESTPLATE -> "HAUBERK";
                case LEGGINGS -> "CHAUSSES";
                case BOOTS -> "PALEOS";
                default -> "";
            };
            case HEAVY -> ItemType.toString(type).toUpperCase();
            default -> "";
        };
        ItemStack armor = ItemCreator.createItem(
                ItemType.toMaterial(weight, type),
                name,
                List.of(
                        "§o§fLv. " + level + "§r" +  ItemRarity.toChatColor(rarity) + "§l " + ItemRarity.toString(rarity).toUpperCase() + " " + typeString,
                        ""
                )
        );

        ItemMeta meta = armor.getItemMeta();

        meta.setUnbreakable(true);
        armor.setItemMeta(meta);
        armor.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);

        ItemSystem.setItemTypes(armor, List.of(type, weight));
        ItemSystem.setRarity(armor, rarity);
        ItemSystem.setLevel(armor, level);
        ItemSystem.setOriginalName(armor, name);
        generateArmorStats(armor, weight, rarity, level);
        ItemSystem.setUsability(armor, true);
        ItemSystem.usableItemCheck(armor, receiver);

        return armor;
    }

    public static void generateArmorStats(ItemStack armor, ItemType weight, ItemRarity rarity, int level) {
        ItemStat mainDefenseStat = switch (weight) {
            case LIGHT -> OVERHEALTH;
            case MEDIUM -> EVASION;
            case HEAVY -> DEFENSE;
            default -> MAXHEALTH;
        };
        ArrayList<ItemStat> uncommonDefenseStat = new ArrayList<>(){{
            addAll(List.of(MAXHEALTH, MAXHEALTH, MAXHEALTH, OVERHEALTH, EVASION, DEFENSE));

            if (weight == ItemType.HEAVY) {
                add(GUARD);
            }
        }};
        List<ItemStat> rareDefenseStat = List.of(PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST, AIRRESIST, RADIANTRESIST, NECROTICRESIST);
        LinkedHashMap<ItemStat, Integer> itemStats = new LinkedHashMap<>(){{
            put(mainDefenseStat, getStatCalculation(mainDefenseStat, weight, rarity, level, 1));
        }};

        if (rarity == ItemRarity.UNCOMMON || rarity == ItemRarity.RARE || rarity == ItemRarity.MYTHICAL) {
            ItemStat secondStat = uncommonDefenseStat.get(ThreadLocalRandom.current().nextInt(uncommonDefenseStat.size()));

            itemStats.merge(secondStat, getStatCalculation(secondStat, weight, rarity, level, 2), Integer::sum);

            if (rarity == ItemRarity.MYTHICAL) {
                ItemStat thirdStat = rareDefenseStat.get(ThreadLocalRandom.current().nextInt(uncommonDefenseStat.size()));

                itemStats.put(thirdStat, getStatCalculation(secondStat, weight, rarity, level, 3));
            }
        }

        ItemSystem.setStats(armor, itemStats);
        ItemSystem.updateLoreWithStats(armor, itemStats);
    }

    // stat slot if its the first, second or third stat slot of the item
    private static int getStatCalculation(ItemStat itemStat, ItemType weight, ItemRarity itemRarity, int level, int statSlot) {
        double finalMultiplier = ItemRarity.getRarityMultiplier(itemRarity) * ThreadLocalRandom.current().nextDouble(.75, 1);
        int statValue = Math.toIntExact(Math.round(
                switch (itemStat) {
                    case EVASION, DEFENSE -> level; // todo: come up with a better calc for these
                    case OVERHEALTH -> (Math.pow((level / 100.0), 1.2) * 980) / 4; // overhealth is the primary stat of light armor
                    case HEALTH -> switch (weight) { // odd multipliers for health to compensate for the statSlotMultiplier
                        case LIGHT -> level * .125;
                        case MEDIUM -> (Math.pow((level / 100.0), 2.5) * 280) / 4;
                        case HEAVY -> (Math.pow((level / 100.0), 1.22) * 730) / 4;
                        default -> -1;
                    };
                    case GUARD -> level;
                    case PHYSICALRESIST, FIRERESIST, COLDRESIST, EARTHRESIST, LIGHTNINGRESIST, AIRRESIST, RADIANTRESIST, NECROTICRESIST -> level * .5;
                    default -> 0;
                } * finalMultiplier)
        );

        // greatly reduce the stat value of the main defense stats if its the secondary roll for that item
        if (statSlot > 1 && (itemStat == EVASION || itemStat == DEFENSE || itemStat == OVERHEALTH)) {
            statValue = Math.toIntExact(Math.round(statValue * .3));
        }

        if (statValue < 1) {
            statValue = 1;
        }

        return statValue;
    }
}
