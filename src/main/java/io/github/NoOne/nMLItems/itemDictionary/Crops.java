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
    public static ItemStack wheatBundle(int level, double stars, int amount, boolean displayItem) {
        ItemStack wheatBundle = new ItemStack(Material.WHEAT, amount);

        setCropKeys(wheatBundle, CropType.WHEAT_BUNDLE, level, stars);

        ItemMeta meta = wheatBundle.getItemMeta();
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Crop";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName("§6Wheat Bundle");
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        wheatBundle.setItemMeta(meta);

        return wheatBundle;
    }

    public static ItemStack sugarCane(int level, double stars, int amount, boolean displayItem) {
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, amount);

        setCropKeys(sugarCane, CropType.SUGAR_CANE, level, stars);

        ItemMeta meta = sugarCane.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Crop";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName("§aSugar Cane");
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));

        pdc.set(ItemSystem.makeItemTypeKey(SEED), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.getSeedTypeString(SeedType.SUGAR_CANE));
        sugarCane.setItemMeta(meta);

        return sugarCane;
    }

    public static ItemStack jadeFlower(int level, double stars, int amount, boolean displayItem) {
        ItemStack jadeFlower = new ItemStack(Material.AZURE_BLUET, amount);

        setCropKeys(jadeFlower, CropType.JADE_FLOWER, level, stars);

        ItemMeta meta = jadeFlower.getItemMeta();
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Crop";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#00A86B>Jade Flower"));
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        jadeFlower.setItemMeta(meta);

        return jadeFlower;
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
}
