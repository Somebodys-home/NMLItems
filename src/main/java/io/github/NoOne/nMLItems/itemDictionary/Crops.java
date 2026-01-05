package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.CROP;
import static io.github.NoOne.nMLItems.ItemType.SEED;

public class Crops {
    public static ItemStack wheatBundle(int level, double stars, int amount) {
        ItemStack wheatBundle = new ItemStack(Material.WHEAT, amount);

        setCropKeys(wheatBundle, CropType.WHEAT_BUNDLE, level, stars);

        ItemMeta meta = wheatBundle.getItemMeta();

        meta.setDisplayName("§6Wheat Bundle");
        meta.setLore(List.of(
                "§8Lv. " + level + " Crop",
                "",
                "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
        ));
        wheatBundle.setItemMeta(meta);

        return wheatBundle;
    }

    public static ItemStack sugarCane(int level, double stars, int amount) {
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, amount);

        setCropKeys(sugarCane, CropType.SUGAR_CANE, level, stars);

        ItemMeta meta = sugarCane.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        meta.setDisplayName("§aSugar Cane");
        meta.setLore(List.of(
                "§8Lv. " + level + " Crop",
                "",
                "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
        ));

        pdc.set(ItemSystem.makeItemTypeKey(SEED), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.getSeedTypeString(SeedType.SUGAR_CANE));
        pdc.set(ItemSystem.getCropConditionKey(), PersistentDataType.STRING, CropCondition.getCropConditionString(CropCondition.WATERED));

        sugarCane.setItemMeta(meta);

        return sugarCane;
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
