package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.NamespacedKey;
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

import static io.github.NoOne.nMLItems.ItemRarity.COMMON;
import static io.github.NoOne.nMLItems.ItemType.HELMET;

public class ItemSystem {
    private static NMLItems nmlItems;
    private static NamespacedKey originalNameKey;
    private static NamespacedKey levelKey;

    public ItemSystem(NMLItems nmlItems) {
        ItemSystem.nmlItems = nmlItems;
        originalNameKey = new NamespacedKey(nmlItems, "original_name");
        levelKey = new NamespacedKey(nmlItems, "level");
    }

    public static void setStat(ItemStack item, ItemStat stat, double amount) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(makeKeyForStat(stat), PersistentDataType.DOUBLE, amount);
        item.setItemMeta(meta);
    }

    public static void setStats(ItemStack item, HashMap<ItemStat, Double> statMap) {
        for (Map.Entry<ItemStat, Double> statEntry : statMap.entrySet()) {
            setStat(item, statEntry.getKey(), statEntry.getValue());
        }
    }

    public static void removeStat(ItemStack item, ItemStat stat) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(makeKeyForStat(stat));
        item.setItemMeta(meta);
    }

    public static void clearStats(ItemStack item) {
        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                removeStat(item, stat);
            }
        }
    }

    public static boolean hasStat(ItemStack item, ItemStat stat) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(makeKeyForStat(stat), PersistentDataType.DOUBLE);
    }

    public static double getStatValue(ItemStack item, ItemStat stat) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (hasStat(item, stat)) {
            return pdc.get(makeKeyForStat(stat), PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public static HashMap<ItemStat, Double> getAllStats(ItemStack item) {
        HashMap<ItemStat, Double> stats = new HashMap<>();

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                stats.put(stat, getStatValue(item, stat));
            }
        }
        return stats;
    }

    public static ItemType getItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemType type : ItemType.values()) {
            if (pdc.has(makeItemTypeKey(type), PersistentDataType.INTEGER)) return type;
        }

        return null;
    }

    public static ItemRarity getItemRarity(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (ItemRarity rarity : ItemRarity.values()) {
            if (pdc.has(makeItemRarityKey(rarity), PersistentDataType.INTEGER)) return rarity;
        }

        return null;
    }

    public static void updateLoreWithStats(ItemStack item) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> originalLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> addedLore = new ArrayList<>();
        HashMap<ItemStat, Double> itemStats = getAllStats(item);

        itemStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Descending sort
                .forEachOrdered(entry -> {
                    double value = entry.getValue();
                    int valueInt = (int) value;
                    addedLore.add(ItemStat.getStatColor(entry.getKey()) + "+ " + valueInt + " " + ItemStat.getStatString(entry.getKey()) + " " + ItemStat.getStatEmoji(entry.getKey()));
                });

        originalLore.addAll(addedLore);
        meta.setLore(originalLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static void updateLoreWithStat(ItemStack item, ItemStat stat, int value) {
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> addedLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        addedLore.add(ItemStat.getStatColor(stat) + "+ " + value + " " + ItemStat.getStatString(stat) + " " + ItemStat.getStatEmoji(stat));
        meta.setLore(addedLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static HashMap<ItemStat, Double> getAllDamageStats(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = new HashMap<>();

        if (!hasDamageStats(item)) return damageStats;

        for (ItemStat stat : ItemStat.values()) {
            if (hasStat(item, stat)) {
                damageStats.put(stat, getStatValue(item, stat));
            }
        }

        return damageStats;
    }

    public static double getTotalDamageOfItem(ItemStack item) {
        HashMap<ItemStat, Double> damageStats = getAllDamageStats(item);
        double totalDamage = 0;

        for (Map.Entry<ItemStat, Double> damageEntry : damageStats.entrySet()) {
            totalDamage += damageEntry.getValue();
        }

        return totalDamage;
    }

    public static HashMap<ItemStat, Double> multiplyAllDamageStats(ItemStack item, double multiplier) {
        HashMap<ItemStat, Double> multipliedDamage = getAllDamageStats(item);

        for (Map.Entry<ItemStat, Double> damageEntry : multipliedDamage.entrySet()) {
            damageEntry.setValue(damageEntry.getValue() * multiplier);
        }

        return multipliedDamage;
    }

    public static boolean hasDamageStats(ItemStack item) {
        return hasStat(item, ItemStat.PHYSICALDAMAGE) ||
                hasStat(item, ItemStat.FIREDAMAGE) ||
                hasStat(item, ItemStat.COLDDAMAGE) ||
                hasStat(item, ItemStat.EARTHDAMAGE) ||
                hasStat(item, ItemStat.LIGHTNINGDAMAGE) ||
                hasStat(item, ItemStat.AIRDAMAGE) ||
                hasStat(item, ItemStat.LIGHTDAMAGE) ||
                hasStat(item, ItemStat.DARKDAMAGE) ||
                hasStat(item, ItemStat.PUREDAMAGE);
    }

    public static String generateItemName(ItemType type, ItemType type2, ItemRarity rarity) {
        String[] nameSegments = null;

        switch (rarity) {
            case COMMON -> {
                nameSegments = new String[2];
                List<String> badAdjectives = new ArrayList<>(List.of("Garbage", "Awful", "Pitiful", "You Deserve This", "Disgusting", "Be Better",
                                                                    "Babies' First", "Oh God That", "Rotten", "Poor", "Degrading", "Forgotten", "Racist"));

                nameSegments[0] = badAdjectives.get(ThreadLocalRandom.current().nextInt(badAdjectives.size()));
            }
            case UNCOMMON -> {
                nameSegments = new String[2];
                List<String> goodAdjectives = new ArrayList<>(List.of("Pretty Alright", "Lifelong", "Based", "Neato Dorito", "Goofy Ahh", "Nobodies'", "Knave's"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(goodAdjectives.size());

                nameSegments[0] = goodAdjectives.get(randomAdjective);
            }
            case RARE -> {
                nameSegments = new String[3];
                List<String> goodAdjectives = new ArrayList<>(List.of("Pretty Alright", "Solid", "Well-Made", "Lifelong", "Based", "W", "Almost Mythical", "Neato Dorito",
                        "Goofy Ahh", "Nobodies'"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(goodAdjectives.size());

                nameSegments[0] = goodAdjectives.get(randomAdjective);
                goodAdjectives.remove(randomAdjective);
                goodAdjectives.remove("Based");
                goodAdjectives.remove("Nobodies'");
                nameSegments[1] = goodAdjectives.get(ThreadLocalRandom.current().nextInt(goodAdjectives.size()));
            }
            case MYTHICAL -> {
                nameSegments = new String[3];
                List<String> greatAdjectives = new ArrayList<>(List.of("Amazing", "Godly", "King's", "Queen's", "Fabled", "Based", "W", "Legendaric", "Goofy Ahh",
                                                                        "Nobodies'"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(greatAdjectives.size());

                nameSegments[0] = greatAdjectives.get(randomAdjective);
                greatAdjectives.remove(randomAdjective);
                greatAdjectives.remove("Based");
                greatAdjectives.remove("King's");
                greatAdjectives.remove("Queen's");
                greatAdjectives.remove("Nobodies'");
                nameSegments[1] = greatAdjectives.get(ThreadLocalRandom.current().nextInt(greatAdjectives.size()));
            }
        }

        assert nameSegments != null;
        switch (type) {
            case SWORD -> {
                List<String> sword = new ArrayList<>(List.of("Sword", "Seax", "Scimitar", "Bigger Knife", "Falchion", "Long Sword"));

                if (rarity == COMMON) {
                    sword.add("Bastard Sword");
                }

                nameSegments[nameSegments.length - 1] = sword.get(ThreadLocalRandom.current().nextInt(sword.size()));
            }
            case DAGGER -> {
                List<String> dagger = new ArrayList<>(List.of("Dagger", "Knife", "Cutlery", "Gillete Razor", "Beard Splitter", "Box Cutter"));
                nameSegments[nameSegments.length - 1] = dagger.get(ThreadLocalRandom.current().nextInt(dagger.size()));
            }
            case AXE -> {
                List<String> axe = new ArrayList<>(List.of("Axe", "Cleaver", "Battle Axe", "Tomahawk", "Chopper", "Box Cutter"));
                nameSegments[nameSegments.length - 1] = axe.get(ThreadLocalRandom.current().nextInt(axe.size()));
            }
            case HAMMER -> {
                List<String> hammer = new ArrayList<>(List.of("Squeaky Toy", "Blunt", "Mallet", "Bonker", "Hammer", "Piko Piko", "Spike Ball"));
                nameSegments[nameSegments.length - 1] = hammer.get(ThreadLocalRandom.current().nextInt(hammer.size()));
            }
            case SPEAR -> {
                List<String> spear = new ArrayList<>(List.of("Giant Arrow", "Javelin", "Military Fork", "Overcompensator", "Trident", "Spear", "Spork"));
                nameSegments[nameSegments.length - 1] = spear.get(ThreadLocalRandom.current().nextInt(spear.size()));
            }
            case GLOVE -> {
                List<String> glove = new ArrayList<>(List.of("Jawbreaker", "TKO", "Rock 'Em", "Sock 'Em", "Failure", "Gloves"));
                nameSegments[nameSegments.length - 1] = glove.get(ThreadLocalRandom.current().nextInt(glove.size()));
            }
            case BOW -> {
                List<String> bow = new ArrayList<>(List.of("Bow", "Peashooter", "Fling Sling", "...Gun?", "Yeet Cannon", "Pew Pew Pew", "Sharpshooter"));
                nameSegments[nameSegments.length - 1] = bow.get(ThreadLocalRandom.current().nextInt(bow.size()));
            }
            case WAND -> {
                List<String> wand = new ArrayList<>(List.of("Wand", "Rabbit Maker", "Boom Stick"));
                nameSegments[nameSegments.length - 1] = wand.get(ThreadLocalRandom.current().nextInt(wand.size()));
            }
            case STAFF -> {
                List<String> staff = new ArrayList<>(List.of("Staff", "Walking Stick", "Cane"));
                nameSegments[nameSegments.length - 1] = staff.get(ThreadLocalRandom.current().nextInt(staff.size()));
            }
            case CATALYST -> {
                List<String> catalyst = new ArrayList<>(List.of("Catalyst", "Grimoire", "Reading Material", "Textbook"));
                nameSegments[nameSegments.length - 1] = catalyst.get(ThreadLocalRandom.current().nextInt(catalyst.size()));
            }
            case SHIELD -> {
                List<String> shield = new ArrayList<>(List.of("Shield", "Buckler", "Kite", "Wall", "Aegis"));
                nameSegments[nameSegments.length - 1] = shield.get(ThreadLocalRandom.current().nextInt(shield.size()));
            }
            case QUIVER -> {
                List<String> quiver = new ArrayList<>(List.of("Quiver", "Stick Bag", "Holster"));
                nameSegments[nameSegments.length - 1] = quiver.get(ThreadLocalRandom.current().nextInt(quiver.size()));
            }
            case LIGHT -> {
                switch (type2) {
                    case HELMET -> {
                        List<String> helmet = new ArrayList<>(List.of("Cap"));
                        nameSegments[nameSegments.length - 1] = helmet.get(ThreadLocalRandom.current().nextInt(helmet.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Shirt"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Pants", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Shoes"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
            case MEDIUM -> {
                switch (type2) {
                    case HELMET ->{
                        List<String> helm = new ArrayList<>(List.of("Coif", "Aventail"));
                        nameSegments[nameSegments.length - 1] = helm.get(ThreadLocalRandom.current().nextInt(helm.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Hauberk"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Chausses", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Paleos"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
            case HEAVY -> {
                switch (type2) {
                    case HELMET -> {
                        List<String> helmet = new ArrayList<>(List.of("Helmet"));
                        nameSegments[nameSegments.length - 1] = helmet.get(ThreadLocalRandom.current().nextInt(helmet.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Chestplate"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Chausses", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Boots"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
        }

        String name = "" + ItemRarity.getItemRarityColor(rarity);
        for (int i = 0; i < nameSegments.length; i++) {
            if (i == nameSegments.length - 1) {
                name += nameSegments[i];
            } else {
                name += nameSegments[i] + " ";
            }
        }

        return name;
    }

    public static String getOriginalItemName(ItemStack item) {
        if (item == null || item.getType().isAir()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(originalNameKey, PersistentDataType.STRING)) return null;

        return pdc.get(originalNameKey, PersistentDataType.STRING);
    }

    public static boolean isItemUsable(ItemStack item, Player player) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Integer itemLevel = pdc.get(levelKey, PersistentDataType.INTEGER);

        if (itemLevel == null) return false;

        int playerLevel = new ProfileManager(NMLItems.getNmlPlayerStats()).getPlayerProfile(player.getUniqueId()).getStats().getLevel();
        return playerLevel >= itemLevel;
    }

    public static void updateUnusableItemName(ItemStack item, boolean usable) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        String originalName = getOriginalItemName(item);
        String editedName;

        if (!usable) {
            editedName = originalName.replaceAll("§[0-9a-fk-or]", "");
            editedName = "§c§m" + editedName;
        } else {
            editedName = originalName;
        }

        // Only update if the name is actually different
        if (!editedName.equals(meta.getDisplayName())) {
            meta.setDisplayName(editedName);
            item.setItemMeta(meta);
        }
    }

    private static NamespacedKey makeKeyForStat(ItemStat stat) {
        return new NamespacedKey(nmlItems, ItemStat.getStatString(stat).replaceAll(" ", ""));
    }

    public static NamespacedKey makeItemTypeKey(ItemType type) {
        return new NamespacedKey(nmlItems, ItemType.getItemTypeString(type));
    }

    public static NamespacedKey makeItemRarityKey(ItemRarity rarity) {
        return new NamespacedKey(nmlItems, ItemRarity.getItemRarityString(rarity));
    }

    public static NamespacedKey getLevelKey() {
        return levelKey;
    }

    public static NamespacedKey getOriginalNameKey() {
        return originalNameKey;
    }
}
