package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.MaterialStars;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.CROP;

public class Crops {
    public static ItemStack wheatBundle(int level, double stars, int amount) {
        ItemStack wheatBundle = new ItemStack(Material.WHEAT, amount);

        setCropKeys(wheatBundle, level, stars);

        ItemMeta meta = wheatBundle.getItemMeta();
        List<String> lore = new ArrayList<>();
        String name = "§6Wheat Bundle";

        meta.setDisplayName(name);
        lore.add("§8Lv. " + level + " Crop");
        lore.add("");
        lore.add("§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >");

        meta.setLore(lore);
        wheatBundle.setItemMeta(meta);

        return wheatBundle;
    }

    private static void setCropKeys(ItemStack itemStack, int level, double stars) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ItemSystem.makeItemTypeKey(CROP), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getLevelKey(), PersistentDataType.INTEGER, level);
        pdc.set(ItemSystem.getStarsKey(), PersistentDataType.DOUBLE, stars);

        itemStack.setItemMeta(meta);
    }
}
