package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
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
import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static io.github.NoOne.nMLItems.ItemRarity.*;
import static io.github.NoOne.nMLItems.ItemStat.*;
import static io.github.NoOne.nMLItems.ItemType.*;

public class WeaponGenerator {
    private static NMLItems nmlItems;

    public WeaponGenerator(NMLItems nmlItems) {
        this.nmlItems = nmlItems;
    }

    public static ItemStack generateWeapon(Player receiver, ItemType type, ItemRarity rarity, int level) {
        ItemStack weapon = new ItemStack(getItemTypeMaterial(type));
        ItemMeta meta = weapon.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        List<String> lore = new ArrayList<>();

        // setting keys
        pdc.set(ItemSystem.makeItemTypeKey(type), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemRarityKey(rarity), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        weapon.setItemMeta(meta);

        // making name
        String name = NameGenerator.generateItemName(type, null, rarity);
        meta.setDisplayName(name);
        pdc.set(ItemSystem.getOriginalNameKey(), PersistentDataType.STRING, name);

        // adding the lore
        lore.add("§o§fLv. " + level + "§r " + getItemRarityColor(rarity) + ChatColor.BOLD + getItemRarityString(rarity).toUpperCase() + " " + getItemTypeString(type).toUpperCase());
        lore.add("");
        meta.setLore(lore);
        weapon.setItemMeta(meta);
        ASCIIArt(weapon, type); // ascii art

        // generating stats
        generateWeaponStats(weapon, type, rarity, level);

        // is weapon unusable?
        ItemSystem.updateUnusableItemName(weapon, ItemSystem.isItemUsable(weapon, receiver));

        // attack speed
        setAttackSpeed(weapon);

        if (type == BOW) weapon.addEnchantment(Enchantment.INFINITY, 1);

        return weapon;
    }

    public static void ASCIIArt(ItemStack weapon, ItemType type) {
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = meta.getLore();

        if (type == SWORD) {
            lore.add("§7        />______________");
            lore.add("§7♦#####[]______________>");
            lore.add("§7        \\>");
        } else if (type == DAGGER) {
            lore.add("§7    ʃ                      ʃ");
            lore.add("§7♦##|======-  -======|##♦");
            lore.add("§7    ʃ                      ʃ");
        } else if (type == AXE) {
            lore.add("§7                           /\\");
            lore.add("§7♦===============######");
            lore.add("§7                       \\_____/");
        } else if (type == HAMMER) {
            lore.add("§7             ╔══╗");
            lore.add("§7♦=======♦|███|♦");
            lore.add("§7             ╚══╝");
        } else if (type == SPEAR) {
            lore.add("§7                           \\`-._");
            lore.add("§7♦========♦========♦   _>");
            lore.add("§7                           /.-'");
        } else if (type == GLOVE) {
            lore.add("§7‾‾‾‾‾‾‾‾‾|♦|‾‾‾‾‾‾‾‾‾");
            lore.add("§7\\_   @_|♦|_@   _/");
            lore.add("§7   \\__)    (__/");
        } else if (type == BOW) {
            lore.add("§7                  ◁----<<");
            lore.add("§7  ︷__♦__︷        >>----▷");
            lore.add("§7/ˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍˍ\\  ◁----<<");
        } else if (type == WAND) {
            lore.add("§7             * ╲ ╱  *");
            lore.add("§7♦========< ⭐ >");
            lore.add("§7          *    ╱ ╲   *");
        } else if (type == STAFF) {
            lore.add("§7                *           ╗ * ╲ ╱  ");
            lore.add("§7♦========♦========♦║ < ⭐ > ");
            lore.add("§7      *                 *   ╝   ╱ ╲   *");
        } else if (type == CATALYST) {
            lore.add("§7  /‾‾/   \\‾‾\\");
            lore.add("§7 <   |  ♦  |   >");
            lore.add("§7  \\_\\    /_/");
        }

        lore.add("");
        meta.setLore(lore);
        weapon.setItemMeta(meta);
    }

    public static void generateWeaponStats(ItemStack weapon, ItemType type, ItemRarity rarity, int level) {
        generateDamage(weapon, type, rarity, level);
        generateSecondaryStats(weapon, rarity, level);
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
                ItemSystem.setStat(weapon, firstType, firstDamageValue);
            }
            case UNCOMMON, RARE -> {
                if (firstType == secondType) {
                    ItemSystem.setStat(weapon, firstType, firstDamageValue + secondDamageValue);
                } else {
                    ItemSystem.setStat(weapon, firstType, firstDamageValue);
                    ItemSystem.setStat(weapon, secondType, secondDamageValue);
                }
            }
            case MYTHICAL -> {
                firstDamageValue = level * 3;

                if (firstType == secondType) {
                    ItemSystem.setStat(weapon, firstType, firstDamageValue + secondDamageValue);
                } else {
                    ItemSystem.setStat(weapon, firstType, firstDamageValue);
                    ItemSystem.setStat(weapon, secondType, secondDamageValue);
                }
            }
        }

        ItemSystem.updateLoreWithStats(weapon);
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

            if (ItemSystem.getItemType(weapon) == GLOVE && randomItemStat == CRITDAMAGE) {
                randomEntry.setValue(randomStatValue * 2);
            }

            selectedStats.merge(randomItemStat, randomStatValue, Integer::sum);
        }

        // update stats
        for (Map.Entry<ItemStat, Integer> selectedStatEntry : selectedStats.entrySet()) {
            ItemSystem.setStat(weapon, selectedStatEntry.getKey(), selectedStatEntry.getValue());
            ItemSystem.updateLoreWithStat(weapon, selectedStatEntry.getKey(), selectedStatEntry.getValue());
        }
    }

    private static void setAttackSpeed(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        double attackspeed = 0;
        AttributeModifier attackSpeedModifier = null;


        switch (ItemSystem.getItemType(weapon)) {
            case SWORD, GLOVE -> attackspeed = -3;
            case DAGGER -> attackspeed = 0;
            case AXE, SPEAR -> attackspeed = -3.5;
            case HAMMER -> attackspeed = -3.66;
            case WAND, STAFF, CATALYST -> attackspeed = -3.13;
        }

        attackSpeedModifier = new AttributeModifier(new NamespacedKey(nmlItems, "attack_speed"), attackspeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        meta.addAttributeModifier(Attribute.ATTACK_SPEED, attackSpeedModifier);
        weapon.setItemMeta(meta);
    }
}
