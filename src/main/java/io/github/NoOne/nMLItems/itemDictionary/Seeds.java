package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.ItemType;
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
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

    public static ItemStack wheatSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack wheatSeeds = ItemCreator.createItem(
                Material.WHEAT_SEEDS,
                amount,
                "§6Wheat Seeds",
                List.of(
                        "§8Lv. " + level + " Seed",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        if (displayItem) {
            itemSystem.turnIntoDisplayItem(wheatSeeds);
        }

        setSeedKeys(wheatSeeds, SeedType.WHEAT_SEEDS, level, stars);
        return wheatSeeds;
    }

    public static ItemStack jadeSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack jadeSeeds = ItemCreator.createItem(
                Material.WHEAT_SEEDS,
                amount,
                MatrixColorAPI.process("<SOLID:#00A86B>Jade Seeds"),
                List.of(
                        "§8Lv. §kX §r§8Seed",
                        "",
                        "§6 < §kaaaaa §r§6>"
                )
        );

        if (displayItem) {
            itemSystem.turnIntoDisplayItem(jadeSeeds);
        }

        setSeedKeys(jadeSeeds, SeedType.JADE_SEEDS, level, stars);
        return jadeSeeds;
    }

    public static ItemStack rhubarbSeeds(int level, double stars, int amount, boolean displayItem) {
        ItemStack rhubarbSeeds = ItemCreator.createItem(
                Material.MELON_SEEDS,
                amount,
                MatrixColorAPI.process("<SOLID:#FC035A>Rhubarb Seeds"),
                List.of(
                        "§8Lv. §kX §r§8Seed",
                        "",
                        "§6 < §kaaaaa §r§6>"
                )
        );

        if (displayItem) {
            itemSystem.turnIntoDisplayItem(rhubarbSeeds);
        }

        setSeedKeys(rhubarbSeeds, SeedType.RHUBARB_SEEDS, level, stars);
        return rhubarbSeeds;
    }

    private static void setSeedKeys(ItemStack itemStack, SeedType seedType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(SEED));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(itemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.toString(seedType));
        itemStack.setItemMeta(meta);
    }
}
