package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.MaterialStars;
import io.github.NoOne.nMLItems.SeedType;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.SEED;

public class Seeds {
    public static ItemStack wheatSeeds(int level, double stars, int amount) {
        ItemStack wheatSeeds = new ItemStack(Material.WHEAT_SEEDS, amount);

        setSeedKeys(wheatSeeds, SeedType.WHEAT_SEEDS, level, stars);

        ItemMeta meta = wheatSeeds.getItemMeta();
        String name = "§6Wheat Seeds";

        meta.setDisplayName(name);
        meta.setLore(List.of(
                "§8Lv. " + level + " Seed",
                "",
                "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
        ));
        wheatSeeds.setItemMeta(meta);

        return wheatSeeds;
    }

    public static ItemStack jadeSeeds(int level, double stars, int amount) {
        ItemStack jadeSeeds = new ItemStack(Material.WHEAT_SEEDS, amount);

        setSeedKeys(jadeSeeds, SeedType.JADE_SEEDS, level, stars);

        ItemMeta meta = jadeSeeds.getItemMeta();
        String name = MatrixColorAPI.process("<SOLID:#00A86B>Jade Seeds");

        meta.setDisplayName(name);
        meta.setLore(List.of(
                "§8Lv. " + level + " Seed",
                "",
                "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
        ));
        jadeSeeds.setItemMeta(meta);

        return jadeSeeds;
    }

    private static void setSeedKeys(ItemStack itemStack, SeedType seedType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ItemSystem.makeItemTypeKey(SEED), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(ItemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(ItemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.getSeedTypeString(seedType));

        itemStack.setItemMeta(meta);
    }
}
