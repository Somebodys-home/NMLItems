package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.enums.MaterialStars;
import io.github.NoOne.nMLItems.enums.SeedType;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemType.SEED;

public class Seeds {
    public static ItemStack wheatSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack wheatSeeds = new ItemStack(Material.WHEAT_SEEDS, amount);

        setSeedKeys(wheatSeeds, SeedType.WHEAT_SEEDS, level, stars);

        ItemMeta meta = wheatSeeds.getItemMeta();
        String levelLine = "§8Lv. " + level + " Seed";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Seed";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName("§6Wheat Seeds");
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        wheatSeeds.setItemMeta(meta);

        return wheatSeeds;
    }

    public static ItemStack jadeSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack jadeSeeds = new ItemStack(Material.WHEAT_SEEDS, amount);

        setSeedKeys(jadeSeeds, SeedType.JADE_SEEDS, level, stars);

        ItemMeta meta = jadeSeeds.getItemMeta();
        String levelLine = "§8Lv. " + level + " Seed";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Seed";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#00A86B>Jade Seeds"));
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        jadeSeeds.setItemMeta(meta);

        return jadeSeeds;
    }

    public static ItemStack rhubarbSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack rhubarbSeeds = new ItemStack(Material.MELON_SEEDS, amount);

        setSeedKeys(rhubarbSeeds, SeedType.RHUBARB_SEEDS, level, stars);

        ItemMeta meta = rhubarbSeeds.getItemMeta();
        String levelLine = "§8Lv. " + level + " Seed";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Seed";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#FC035A>Rhubarb Seeds"));
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        rhubarbSeeds.setItemMeta(meta);

        return rhubarbSeeds;
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
