package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.NMLItems;
import io.github.NoOne.nMLItems.enums.IngredientType;
import io.github.NoOne.nMLItems.enums.ItemType;
import io.github.NoOne.nMLItems.enums.MaterialStars;
import net.matrixcreations.libraries.MatrixColorAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.enums.ItemType.INGREDIENT;
import static io.github.NoOne.nMLItems.enums.ItemType.MISC;

public class Miscellaneous {
    private static NMLItems nmlItems = NMLItems.getInstance();
    private static ItemSystem itemSystem = nmlItems.getItemSystem();

    public static ItemStack burntFood(ItemStack previousFood) {
        String name = ChatColor.stripColor(previousFood.getItemMeta().getDisplayName());
        ItemStack burntFood = ItemCreator.createItem(
                Material.GUNPOWDER,
                previousFood.getAmount(),
                MatrixColorAPI.process("§7Burnt " + name),
                List.of(
                        "§8Lv. §kX §r§8Mistake",
                        "",
                        "§7§oBe ashamed.",
                        "",
                        "§6 < §kxxxxx §r§6>"
                )
        );

        setMiscKey(burntFood);
        return burntFood;
    }

    private static void setMiscKey(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(itemSystem.getItemTypeKey(), PersistentDataType.STRING, ItemType.toString(MISC));
        itemStack.setItemMeta(meta);
    }
}
