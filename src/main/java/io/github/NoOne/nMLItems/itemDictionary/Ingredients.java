package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.*;
import net.kyori.adventure.key.Key;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemType.*;
import static io.papermc.paper.datacomponent.DataComponentTypes.*;

public class Ingredients {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static ItemSystem itemSystem = nmlItems.getItemSystem();

    public static ItemStack flour(ItemStack inputItem, int amount) {
        String firstName = inputItem.getItemMeta().getDisplayName().substring(2).split(" ")[0];
        int level = itemSystem.getLevel(inputItem);
        double stars = itemSystem.getStars(inputItem);
        ItemStack flour = ItemCreator.createItem(
                Material.SUGAR,
                amount,
                MatrixColorAPI.process("<SOLID:#f0e5c7>" + firstName + " Flour"),
                List.of(
                        "§8Lv. " + level + " Ingredient",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setIngredientKeys(flour, IngredientType.FLOUR, level, stars);
        return flour;
    }

    public static ItemStack pieCrust(int level, double stars, int amount) {
        ItemStack pieCrust = ItemCreator.createItem(
                Material.MUSHROOM_STEW,
                amount,
                MatrixColorAPI.process("<SOLID:#f0e5c7>Pie Crust"),
                List.of(
                        "§8Lv. " + level + " Ingredient",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setIngredientKeys(pieCrust, IngredientType.PIE_CRUST, level, stars);
        pieCrust.setData(ITEM_MODEL, new NamespacedKey("nml", "pie_crust"));
        return pieCrust;
    }

    public static ItemStack bakedPieCrust(int level, double stars, int amount) {
        ItemStack bakedPieCrust = ItemCreator.createItem(
                Material.BOWL,
                amount,
                MatrixColorAPI.process("<SOLID:#DB9015>Baked Pie Crust"),
                List.of(
                        "§8Lv. " + level + " Ingredient",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );
        setIngredientKeys(bakedPieCrust, IngredientType.BAKED_PIE_CRUST, level, stars);
        bakedPieCrust.setData(ITEM_MODEL, new NamespacedKey("nml", "baked_pie_crust"));
        bakedPieCrust.setData(MAX_STACK_SIZE, 1);
        return bakedPieCrust;
    }

    public static ItemStack bottleOfWater(int level, double stars, int amount) {
        ItemStack bottleOfWater = ItemCreator.createItem(
                Material.POTION,
                amount,
                MatrixColorAPI.process("§bBottle of Water"),
                List.of(
                        "§8Lv. " + level + " Ingredient",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        bottleOfWater.unsetData(POTION_CONTENTS);
        setIngredientKeys(bottleOfWater, IngredientType.WATER, level, stars);
        return bottleOfWater;
    }

    public static ItemStack sugar(int level, double stars, int amount) {
        ItemStack flour = ItemCreator.createItem(
                Material.SUGAR,
                amount,
                MatrixColorAPI.process("<SOLID:#e6faf7>Sugar"),
                List.of(
                        "§8Lv. " + level + " Ingredient",
                        "",
                        "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >"
                )
        );

        setIngredientKeys(flour, IngredientType.FLOUR, level, stars);
        return flour;
    }

    private static void setIngredientKeys(ItemStack itemStack, IngredientType ingredientType, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(INGREDIENT));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(itemSystem.getIngredientKey(), PersistentDataType.STRING, IngredientType.toString(ingredientType));
        itemStack.setItemMeta(meta);
    }
}
