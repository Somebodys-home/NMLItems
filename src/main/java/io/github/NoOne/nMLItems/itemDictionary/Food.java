package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.IngredientType;
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

import static io.github.NoOne.nMLItems.enums.ItemType.INGREDIENT;
import static io.papermc.paper.datacomponent.DataComponentTypes.*;

public class Food {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static ItemSystem itemSystem = nmlItems.getItemSystem();

    public static ItemStack rhubarbPie(int level, double stars, int amount) {
        ItemStack pieCrust = ItemCreator.createItem(
                Material.MUSHROOM_STEW,
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

        setFoodKeys(pieCrust, level, stars);
        pieCrust.setData(ITEM_MODEL, new NamespacedKey("nml", "rhubarb_pie"));
        return pieCrust;
    }
    private static void setFoodKeys(ItemStack itemStack, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(ItemType.FOOD));
        pdc.set(itemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(itemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);
        itemStack.setItemMeta(meta);
    }
}
