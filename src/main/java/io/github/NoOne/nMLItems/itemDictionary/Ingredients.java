package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.CropType;
import io.github.NoOne.nMLItems.enums.IngredientType;
import io.github.NoOne.nMLItems.enums.MaterialStars;
import io.github.NoOne.nMLItems.enums.SeedType;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemType.*;

public class Ingredients {
    private static ItemSystem itemSystem = NMLItems.getInstance().getItemSystem();

    public static ItemStack flour(int level, double stars, int amount) {
        ItemStack flour = ItemCreator.createItem(
                Material.SUGAR,
                amount,
                MatrixColorAPI.process("<SOLID:#f0e5c7>Flour"),
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

        pdc.set(itemSystem.makeItemTypeKey(INGREDIENT), PersistentDataType.INTEGER, 1);
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        pdc.set(itemSystem.getIngredientKey(), PersistentDataType.STRING, IngredientType.getIngredientTypeString(ingredientType));
        itemStack.setItemMeta(meta);
    }
}
