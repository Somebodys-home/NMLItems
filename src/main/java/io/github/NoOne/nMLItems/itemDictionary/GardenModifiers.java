package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemSystem;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.FERTILIZER;
import static io.github.NoOne.nMLItems.ItemType.SEED;

public class GardenModifiers {
    public static ItemStack fertilizer() {
        ItemStack fertilizer = new ItemStack(Material.BROWN_WOOL);
        ItemMeta meta = fertilizer.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        List<String> lore = new ArrayList<>();

        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#5C4033>Fertilizer"));
        lore.add("§7§lCommon Garden Modifier");
        lore.add("");
        lore.add("§6 < Adds §a+20 Harvest \uD83E\uDEB4 §r§6to your garden. >");
        lore.add("");
        lore.add("§8§oMade from demonic excrements,");
        lore.add("§8§ofor all you know.");

        pdc.set(ItemSystem.makeItemTypeKey(FERTILIZER), PersistentDataType.INTEGER, 1);

        meta.setLore(lore);
        fertilizer.setItemMeta(meta);

        return fertilizer;
    }
}
