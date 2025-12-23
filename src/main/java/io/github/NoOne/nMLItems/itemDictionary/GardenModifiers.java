package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemRarity;
import io.github.NoOne.nMLItems.ItemSystem;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.*;

public class GardenModifiers {
    public static ItemStack fertilizer() {
        ItemStack fertilizer = new ItemStack(Material.BROWN_WOOL);
        ItemMeta meta = fertilizer.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#5C4033>Fertilizer"));
        meta.setLore(List.of(
                "§7§lCommon Garden Modifier",
                "",
                "§6 < Adds §a+20 Harvest \uD83E\uDEB4 §r§6to your garden. >",
                "",
                "§8§oMade from demonic excrements,",
                "§8§ofor all you know."));

        pdc.set(ItemSystem.makeItemTypeKey(FERTILIZER), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemTypeKey(GARDEN_MODIFIER), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemRarityKey(ItemRarity.COMMON), PersistentDataType.INTEGER, 1);
        fertilizer.setItemMeta(meta);

        return fertilizer;
    }

    public static ItemStack wateringCan() {
        ItemStack wateringCan = new ItemStack(Material.WATER_BUCKET);
        ItemMeta meta = wateringCan.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        meta.setDisplayName(MatrixColorAPI.process("§bWatering Can"));
        meta.setLore(List.of(
                "§7§lCommon Garden Modifier",
                "",
                "§6 < Adds §a+20 Yield \uD83E\uDD55 §r§6to your garden. >",
                "",
                "§8§oStay Hydrated!"));

        pdc.set(ItemSystem.makeItemTypeKey(WATERING_CAN), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemTypeKey(GARDEN_MODIFIER), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.makeItemRarityKey(ItemRarity.COMMON), PersistentDataType.INTEGER, 1);
        wateringCan.setItemMeta(meta);

        return wateringCan;
    }
}
