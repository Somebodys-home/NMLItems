package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static io.github.NoOne.nMLItems.enums.ItemRarity.*;
import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class Weapons {
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

    public static ItemStack generateWeapon(Player receiver, ItemType type, ItemRarity rarity, int level) {
        String name = NameGenerator.generateItemName(type, null, rarity);
        ArrayList<String> lore = new ArrayList<>(List.of(
                "§o§fLv. " + level + "§r " + toChatColor(rarity) + ChatColor.BOLD + ItemRarity.toString(rarity).toUpperCase() + " " + ItemType.toString(type).toUpperCase(),
                ""
        ));

        lore.addAll(makeWeaponASCIIArt(type));

        ItemStack weapon = ItemCreator.createItem(
                toMaterial(type),
                name,
                lore
        );
        ItemMeta meta = weapon.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(type));
        pdc.set(itemSystem.getRarityKey(), PersistentDataType.STRING, ItemRarity.toString(rarity));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        meta.setMaxStackSize(1);
        weapon.setItemMeta(meta);

        generateDamage(weapon, type, rarity, level);
        generateSecondaryStats(weapon, rarity, level);
        itemSystem.updateUnusableItemName(weapon, itemSystem.isItemUsable(weapon, receiver));
        setAttackSpeed(weapon);

        if (type == BOW) {
            weapon.addEnchantment(Enchantment.INFINITY, 1);
        }

        return weapon;
    }

    private static void generateDamage(ItemStack weapon, ItemType type, ItemRarity rarity, int level) {
        List<ItemStat> possibleFirstDamageTypes = null;
        List<ItemStat> possibleSecondDamageTypes = null;

        switch (type) {
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
        int firstDamageValue = level * 2;
        ItemStat secondType = possibleSecondDamageTypes.get(ThreadLocalRandom.current().nextInt(possibleSecondDamageTypes.size()));
        int secondDamageValue = level;

        switch (rarity) {
            case COMMON -> {
                itemSystem.setStat(weapon, firstType, firstDamageValue);
            }
            case UNCOMMON, RARE -> {
                if (firstType == secondType) {
                    itemSystem.setStat(weapon, firstType, firstDamageValue + secondDamageValue);
                } else {
                    itemSystem.setStat(weapon, firstType, firstDamageValue);
                    itemSystem.setStat(weapon, secondType, secondDamageValue);
                }
            }
            case MYTHICAL -> {
                firstDamageValue = level * 3;

                if (firstType == secondType) {
                    itemSystem.setStat(weapon, firstType, firstDamageValue + secondDamageValue);
                } else {
                    itemSystem.setStat(weapon, firstType, firstDamageValue);
                    itemSystem.setStat(weapon, secondType, secondDamageValue);
                }
            }
        }

        itemSystem.updateEquipmentLoreWithStats(weapon);
    }

    private static void generateSecondaryStats(ItemStack weapon, ItemRarity rarity, int level) {
        HashMap<ItemStat, Integer> statMap = new HashMap<>();
            statMap.put(CRITCHANCE, level * 2);
            statMap.put(CRITDAMAGE, level * 10);

        // divider
        if (rarity != COMMON) {
            ItemMeta meta = weapon.getItemMeta();
            List<String> addedLore = meta.getLore();

            addedLore.add("§7─────────────");
            meta.setLore(addedLore);
            weapon.setItemMeta(meta);
        }

        // generate stat rolls
        List<Map.Entry<ItemStat, Integer>> statEntries = new ArrayList<>(statMap.entrySet());
        HashMap<ItemStat, Integer> selectedStats = new HashMap<>();
        int rolls = 0;

        switch (rarity) {
            case UNCOMMON -> rolls = 1;
            case RARE -> rolls = 2;
            case MYTHICAL -> rolls = 4;
        }

        for (int i = 0; i < rolls; i++) {
            Map.Entry<ItemStat, Integer> randomEntry = statEntries.get(new Random().nextInt(statEntries.size()));
            ItemStat randomItemStat = randomEntry.getKey();
            int randomStatValue = randomEntry.getValue();

            if (itemSystem.getItemType(weapon) == GLOVE && randomItemStat == CRITDAMAGE) {
                randomEntry.setValue(randomStatValue * 2);
            }

            selectedStats.merge(randomItemStat, randomStatValue, Integer::sum);
        }

        // update stats
        for (Map.Entry<ItemStat, Integer> selectedStatEntry : selectedStats.entrySet()) {
            itemSystem.setStat(weapon, selectedStatEntry.getKey(), selectedStatEntry.getValue());
            itemSystem.updateLoreWithStat(weapon, selectedStatEntry.getKey(), selectedStatEntry.getValue());
        }
    }

    private static void setAttackSpeed(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        double attackspeed = 0;
        AttributeModifier attackSpeedModifier;


        switch (itemSystem.getItemType(weapon)) {
            case SWORD, GLOVE, SPEAR -> attackspeed = -3;
            case DAGGER -> attackspeed = 0;
            case AXE -> attackspeed = -3.5;
            case HAMMER -> attackspeed = -3.66;
            case WAND, STAFF, CATALYST -> attackspeed = -3.13;
        }

        attackSpeedModifier = new AttributeModifier(new NamespacedKey(NMLItems.getInstance(), "attack_speed"), attackspeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        meta.addAttributeModifier(Attribute.ATTACK_SPEED, attackSpeedModifier);
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
}
