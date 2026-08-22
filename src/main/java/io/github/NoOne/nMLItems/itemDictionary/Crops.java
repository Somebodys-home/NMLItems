package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
import io.github.NoOne.nMLItems.enums.*;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemStat.*;
import static io.github.NoOne.nMLItems.enums.ItemType.*;
import static io.papermc.paper.datacomponent.DataComponentTypes.ITEM_MODEL;

public class Crops {
    public static ItemStack wheatBundle(int level, double stars, int amount) {
        HashMap<ItemStat, Double> itemStats = new HashMap<>(){{
            put(HEALTH, ItemSystem.calcCropStatValue(CropType.WHEAT_BUNDLE, level, stars));
        }};
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
        ItemSystem.setStats(wheatBundle, itemStats);
        ItemSystem.updateItemLoreWithStats(wheatBundle);
        return wheatBundle;
    }

    public static ItemStack sugarCane(int level, double stars, int amount, boolean displayItem) {
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";
        HashMap<ItemStat, Double> itemStats = new HashMap<>(){{
            put(SPEED, ItemSystem.calcCropStatValue(CropType.SUGAR_CANE, level, stars));
        }};
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
        ItemSystem.setStats(sugarCane, itemStats);
        ItemSystem.updateItemLoreWithStats(sugarCane);

        if (displayItem) {
            ItemSystem.turnIntoDisplayItem(sugarCane);
        }

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

    public static ItemStack rhubarb(int level, double stars, int amount, boolean displayItem) {
        HashMap<ItemStat, Double> itemStats = new HashMap<>(){{
            put(PHYSICALDAMAGE, ItemSystem.calcCropStatValue(CropType.RHUBARB, level, stars));
        }};
        ItemStack rhubarb = ItemCreator.createItem(
                Material.MANGROVE_PROPAGULE,
                amount,
                MatrixColorAPI.process("<SOLID:#FC035A>Rhubarb"),
                List.of(
                        "§8Lv. " + level + " Crop, Ingredient",
                        "",
                        "§7§oRhuBARB indeed. Ow.",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setCropKeys(rhubarb, CropType.RHUBARB, level, stars);
        setIngredientKeys(rhubarb, IngredientType.RHUBARB);
        ItemSystem.setStats(rhubarb, itemStats);
        ItemSystem.updateItemLoreWithStats(rhubarb);
        rhubarb.setData(ITEM_MODEL, new NamespacedKey("nml", "rhubarb"));

        if (displayItem) {
            ItemSystem.turnIntoDisplayItem(rhubarb);
        }

        return rhubarb;
    }

    private static void setCropKeys(ItemStack itemStack, CropType cropType, int level, double stars) {
        ItemSystem.setItemType(itemStack, CROP);
        ItemSystem.setCropType(itemStack, cropType);
        ItemSystem.setLevel(itemStack, level);
        ItemSystem.setStars(itemStack, stars);
    }

    private static void setCropAndSeedKeys(ItemStack itemStack, CropType cropType, SeedType seedType, int level, double stars) {
        setCropKeys(itemStack, cropType, level, stars);
        ItemSystem.setSecondaryType(itemStack, SEED);
        ItemSystem.setSeedType(itemStack, seedType);
    }

    private static void setIngredientKeys(ItemStack itemStack, IngredientType ingredientType) {
        ItemSystem.setSecondaryType(itemStack, INGREDIENT);
        ItemSystem.setIngredientType(itemStack, ingredientType);
    }
}
