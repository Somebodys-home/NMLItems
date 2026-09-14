package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemRarity.COMMON;
import static io.github.NoOne.nMLItems.enums.ItemRarity.toChatColor;
import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class Weapons {
    public static ItemStack generateWeapon(Player receiver, ItemType itemType, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(itemType, null, rarity);
        ArrayList<String> lore = new ArrayList<>(List.of(
                "§o§fLv. " + level + "§r " + toChatColor(rarity) + "§l" + ItemRarity.toString(rarity).toUpperCase() + " " + ItemType.toString(itemType).toUpperCase(),
                ""
        ));

        lore.addAll(makeWeaponASCIIArt(itemType));

        ItemStack weapon = ItemCreator.createItem(ItemType.toMaterial(itemType), name, lore);
        ItemMeta meta = weapon.getItemMeta();

        meta.setUnbreakable(true);
        meta.setMaxStackSize(1);
        weapon.setItemMeta(meta);
        weapon.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

        ItemSystem.setItemType(weapon, itemType);
        ItemSystem.setRarity(weapon, rarity);
        ItemSystem.setLevel(weapon, level);
        ItemSystem.setOriginalName(weapon, name);
        ItemSystem.setUsability(weapon, true);
        ItemSystem.usableItemCheck(weapon, receiver);
        generateDamage(weapon, itemType, rarity, level);
        generateSecondaryStats(weapon, rarity, level);
        setAttackSpeed(weapon);

        if (itemType == BOW) {
            weapon.addEnchantment(Enchantment.INFINITY, 1);
        }

        return weapon;
    }

    private static void generateDamage(ItemStack weapon, ItemType itemType, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstDamageTypes = null;
        List<ItemStat> possibleSecondDamageTypes = null;
        HashMap<ItemStat, Double> itemStats = new HashMap<>();

        switch (itemType) {
            case SWORD, AXE, HAMMER, SPEAR, GLOVE -> {
                possibleFirstDamageTypes = new ArrayList<>(List.of(PHYSICALDAMAGE));
                possibleSecondDamageTypes = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE));
            }
            case DAGGER, BOW -> {
                possibleFirstDamageTypes = new ArrayList<>(List.of(PHYSICALDAMAGE));
                possibleSecondDamageTypes = new ArrayList<>(List.of(PHYSICALDAMAGE, FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE,
                                                                    NECROTICDAMAGE, PUREDAMAGE));
            }
            case WAND, STAFF, CATALYST -> {
                possibleFirstDamageTypes = new ArrayList<>(List.of(FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE, NECROTICDAMAGE));
                possibleSecondDamageTypes = new ArrayList<>(List.of(FIREDAMAGE, COLDDAMAGE, EARTHDAMAGE, LIGHTNINGDAMAGE, AIRDAMAGE, RADIANTDAMAGE, NECROTICDAMAGE));
            }
        }

        ItemStat firstType = possibleFirstDamageTypes.get(ThreadLocalRandom.current().nextInt(possibleFirstDamageTypes.size()));
        double firstDamageValue = doDamageCalc(rarity, level);
        ItemStat secondType = possibleSecondDamageTypes.get(ThreadLocalRandom.current().nextInt(possibleSecondDamageTypes.size()));
        double secondDamageValue = (int) Math.round(firstDamageValue * ThreadLocalRandom.current().nextDouble(.35, .5));

        switch (rarity) {
            case COMMON -> itemStats.put(firstType, firstDamageValue);
            case UNCOMMON, RARE -> {
                if (firstType == secondType) {
                    itemStats.put(firstType, firstDamageValue + secondDamageValue);
                } else {
                    itemStats.put(firstType, firstDamageValue);
                    itemStats.put(secondType, secondDamageValue);
                }
            }
            case MYTHICAL -> {
                firstDamageValue = level * 3;

                if (firstType == secondType) {
                    itemStats.put(firstType, firstDamageValue + secondDamageValue);
                } else {
                    itemStats.put(firstType, firstDamageValue);
                    itemStats.put(secondType, secondDamageValue);
                }
            }
        }

        ItemSystem.setStats(weapon, itemStats);
        ItemSystem.updateLoreWithStats(weapon, ItemSystem.sortStats(itemStats));
    }

    private static void generateSecondaryStats(ItemStack weapon, ItemRarity rarity, int level) {
        if (rarity == COMMON) return; // common items don't get secondary stats

        HashMap<ItemStat, Integer> potentialStats = new HashMap<>(){{ // the integer is the equation for that stat
            put(CRITCHANCE, level * 2);
            put(CRITDAMAGE, level * 10);
        }};
        HashMap<ItemStat, Integer> selectedStats = new HashMap<>();
        int rolls = switch (rarity) {
            case UNCOMMON -> 1;
            case RARE -> 2;
            case MYTHICAL -> 3;
            default -> 0;
        };

        // divider
        ItemSystem.addLoreToItem(weapon, List.of("§7─────────────"));

        // stat rolls
        for (int i = 0; i < rolls; i++) {
            Map.Entry<ItemStat, Integer> randomEntry = potentialStats.entrySet().stream().toList().get(new Random().nextInt(potentialStats.size()));

            selectedStats.merge(randomEntry.getKey(),  randomEntry.getValue(), Integer::sum);
        }

        ItemSystem.setStats(weapon, selectedStats);
        ItemSystem.updateLoreWithStats(weapon, selectedStats);
    }

    private static void setAttackSpeed(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        double attackSpeed = 0;

        if (ItemSystem.isItemType(weapon, SWORD) || ItemSystem.isItemType(weapon, GLOVE) || ItemSystem.isItemType(weapon, SPEAR)) {
            attackSpeed = -3;
        } else if (ItemSystem.isItemType(weapon, DAGGER)) {
            attackSpeed = 0;
        } else if (ItemSystem.isItemType(weapon, AXE)) {
            attackSpeed = -3.5;
        } else if (ItemSystem.isItemType(weapon, HAMMER)) {
            attackSpeed = -3.66;
        } else if (ItemSystem.isItemType(weapon, WAND) || ItemSystem.isItemType(weapon, STAFF) || ItemSystem.isItemType(weapon, CATALYST)) {
            attackSpeed = -3.13;
        }

        meta.addAttributeModifier(Attribute.ATTACK_SPEED, new AttributeModifier(new NamespacedKey(NMLItems.getInstance(), "attack_speed"), attackSpeed,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND));
        weapon.setItemMeta(meta);
    }

    private static List<String> makeWeaponASCIIArt(ItemType type) {
        List<String> ASCII = new ArrayList<>();

        if (type == SWORD) {
            ASCII.add("§7        />______________");
            ASCII.add("§7♦#####[]______________>");
            ASCII.add("§7        \\>");
        } else if (type == DAGGER) {
            ASCII.add("§7    ʃ                      ʃ");
            ASCII.add("§7♦##|======-  -======|##♦");
            ASCII.add("§7    ʃ                      ʃ");
        } else if (type == AXE) {
            ASCII.add("§7                           /\\");
            ASCII.add("§7♦===============######");
            ASCII.add("§7                       \\_____/");
        } else if (type == HAMMER) {
            ASCII.add("§7             ╔══╗");
            ASCII.add("§7♦=======♦|███|♦");
            ASCII.add("§7             ╚══╝");
        } else if (type == SPEAR) {
            ASCII.add("§7                           \\`-._");
            ASCII.add("§7♦========♦========♦   _>");
            ASCII.add("§7                           /.-'");
        } else if (type == GLOVE) {
            ASCII.add("§7    ‾‾‾‾‾‾‾‾‾|♦|‾‾‾‾‾‾‾‾‾");
            ASCII.add("§7    \\_   @_|♦|_@   _/");
            ASCII.add("§7       \\__)    (__/");
        } else if (type == BOW) {
            ASCII.add("§7                  ◁----<<");
            ASCII.add("§7  ︷__♦__︷        >>----▷");
            ASCII.add("§7/ˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍ\\  ◁----<<");
        } else if (type == WAND) {
            ASCII.add("§7            * ╲  ╱  *");
            ASCII.add("§7♦========< ⭐ >");
            ASCII.add("§7          *   ╱  ╲   *");
        } else if (type == STAFF) {
            ASCII.add("§7                   *        ╗  ╲  ╱  ");
            ASCII.add("§7♦========♦========♦║ < ⭐ > ");
            ASCII.add("§7      *                 *   ╝  ╱  ╲   *");
        } else if (type == CATALYST) {
            ASCII.add("§7          /‾‾/   \\‾‾\\");
            ASCII.add("§7         <   |  ♦  |   >");
            ASCII.add("§7          \\_\\    /_/");
        }

        ASCII.add("");
        return ASCII;
    }

    // return what the baseline damage of a weapon should be for its level and rarity
    private static int doDamageCalc(ItemRarity itemRarity, int level) {
        double multiplier = ItemRarity.getRarityMultiplier(itemRarity) * ThreadLocalRandom.current().nextDouble(.75, 1);
        double baseDamage = (1 + (1.5 * level) / 100.0) * level + 2;

        return (int) (multiplier * baseDamage);
    }
}
