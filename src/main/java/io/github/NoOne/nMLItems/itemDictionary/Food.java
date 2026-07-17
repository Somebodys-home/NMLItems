package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.FoodType;
import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.enums.ItemType;
import io.github.NoOne.nMLItems.enums.MaterialStars;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.NoOne.nMLItems.enums.FoodType.*;
import static io.papermc.paper.datacomponent.DataComponentTypes.*;

public class Food {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static ItemSystem itemSystem = nmlItems.getItemSystem();

    public static ItemStack rhubarbPie(ItemStack filledPieCrust, int level, double stars, int amount) {
        HashMap<ItemStat, Double> itemStats = new HashMap<>();
        HashMap<ItemStat, Double> filledPieCrustStats = itemSystem.getAllStats(filledPieCrust);

        for (ItemStack itemStack : itemSystem.getAllItemsInPie(filledPieCrust)) {
            HashMap<ItemStat, Double> itemStat = itemSystem.getAllStats(itemStack);

            for (Map.Entry<ItemStat, Double> entry : itemStat.entrySet()) {
                if (itemStats.containsKey(entry.getKey())) {
                    itemStats.put(entry.getKey(), itemStats.get(entry.getKey()) + entry.getValue());
                } else {
                    itemStats.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (Map.Entry<ItemStat, Double> entry : filledPieCrustStats.entrySet()) {
            if (itemStats.containsKey(entry.getKey())) {
                itemStats.put(entry.getKey(), itemStats.get(entry.getKey()) + entry.getValue());
            } else {
                itemStats.put(entry.getKey(), entry.getValue());
            }
        }

        ItemStack rhubarbPie = ItemCreator.createItem(
                Material.APPLE,
                amount,
                MatrixColorAPI.process("<SOLID:#FC035A>Rhubarb Pie"),
                List.of(
                        "§8Lv. " + level + " Dish",
                        "",
                        "§7§oThe gap moe of baked food;",
                        "§7§oonce prickly, now made sweet.",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setFoodKeys(rhubarbPie, RHUBARB_PIE, level, stars);
        itemSystem.setStats(rhubarbPie, itemStats);
        updateFoodLoreWithEffects(rhubarbPie);
        addServingsToLore(rhubarbPie, RHUBARB_PIE);
        rhubarbPie.setData(ITEM_MODEL, new NamespacedKey("nml", "rhubarb_pie"));
        rhubarbPie.setData(MAX_STACK_SIZE, 1);
        return rhubarbPie;
    }

    public static String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (totalSeconds == 0) {
            return "";
        } else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    private static void setFoodKeys(ItemStack itemStack, FoodType foodType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(ItemType.FOOD));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(itemSystem.getServingsKey(), PersistentDataType.INTEGER, getServings(foodType));
        pdc.set(itemSystem.getFoodTypeKey(), PersistentDataType.STRING, FoodType.toString(foodType));
        itemStack.setItemMeta(meta);
    }

    private static void updateFoodLoreWithEffects(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(meta.getLore());
        String starString = lore.getLast();
        LinkedHashMap<ItemStat, Double> sortedStats = itemSystem.getAllStats(itemStack).entrySet().stream()
                .sorted(Map.Entry.<ItemStat, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, _) -> e1,
                        LinkedHashMap::new
                ));

        lore.removeLast();

        for (Map.Entry<ItemStat, Double> entry : sortedStats.entrySet()) {
            int seconds = ItemStat.getSeconds(entry.getKey());
            double value = entry.getValue();
            String time = "";

            if (seconds > 0) {
                time = " §r§8(" + formatTime(ItemStat.getSeconds(entry.getKey())) + ")";
            }

            if (value == (int) value) {
                lore.add(itemSystem.makeItemStatString(entry.getKey(), (int) value) + time);
            } else {
                lore.add(itemSystem.makeItemStatString(entry.getKey(), entry.getValue()) + time);
            }
        }

        lore.addAll(List.of("", starString));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    private static void addServingsToLore(ItemStack itemStack, FoodType foodType) {
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(meta.getLore());
        String starString = lore.getLast();
        String servingsString = "§e§oServings: " + getServings(foodType) + "/" + getServings(foodType);

        lore.removeLast();
        lore.addAll(List.of(servingsString, "", starString));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }
}
