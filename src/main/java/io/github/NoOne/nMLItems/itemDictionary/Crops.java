package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
import io.github.NoOne.nMLItems.enums.CropType;
import io.github.NoOne.nMLItems.enums.MaterialStars;
import io.github.NoOne.nMLItems.enums.SeedType;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemType.CROP;
import static io.github.NoOne.nMLItems.enums.ItemType.SEED;

public class Crops {
    public static ItemStack wheatBundle(int level, double stars, int amount) {
        ItemStack wheatBundle = ItemCreator.createItem(
                Material.WHEAT,
                amount,
                "§6Wheat Bundle",
                List.of(
                        "§8Lv. " + level + " Crop",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setCropKeys(wheatBundle, CropType.WHEAT_BUNDLE, level, stars);
        return wheatBundle;
    }

    public static ItemStack sugarCane(int level, double stars, int amount, boolean displayItem) {
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Crop";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        ItemStack sugarCane = ItemCreator.createItem(
                Material.SUGAR_CANE,
                amount,
                "§aSugar Cane",
                List.of(
                        levelLine,
                        "",
                        starLine
                )
        );

        setCropAndSeedKeys(sugarCane, CropType.SUGAR_CANE, SeedType.SUGAR_CANE, level, stars);
        return sugarCane;
    }

    public static ItemStack jadeFlower(int level, double stars, int amount) {
        ItemStack jadeFlower = ItemCreator.createItem(
                Material.AZURE_BLUET,
                amount,
                MatrixColorAPI.process("<SOLID:#00A86B>Jade Flower"),
                List.of(
                        "§8Lv. " + level + " Crop",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setCropKeys(jadeFlower, CropType.JADE_FLOWER, level, stars);
        return jadeFlower;
    }

    public static ItemStack rhubarb(int level, double stars, int amount) {
        ItemStack rhubarb = ItemCreator.createItem(
                Material.MANGROVE_PROPAGULE,
                amount,
                MatrixColorAPI.process("<SOLID:#FC035A>Rhubarb"),
                List.of(
                        "§8Lv. " + level + " Crop",
                        "",
                        "§7§oRhuBARB indeed. Ow.",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setCropKeys(rhubarb, CropType.RHUBARB, level, stars);
        return rhubarb;
    }

    private static void setCropKeys(ItemStack itemStack, CropType cropType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ItemSystem.makeItemTypeKey(CROP), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(ItemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(ItemSystem.getCropKey(), PersistentDataType.STRING, CropType.getCropTypeString(cropType));
        itemStack.setItemMeta(meta);
    }

    private static void setCropAndSeedKeys(ItemStack itemStack, CropType cropType, SeedType seedType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ItemSystem.makeItemTypeKey(CROP), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemTypeKey(SEED), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(ItemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(ItemSystem.getCropKey(), PersistentDataType.STRING, CropType.getCropTypeString(cropType));
        pdc.set(ItemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.getSeedTypeString(seedType));
        itemStack.setItemMeta(meta);
    }
}
