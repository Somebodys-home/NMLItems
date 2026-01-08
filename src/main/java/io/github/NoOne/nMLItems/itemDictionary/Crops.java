package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.*;
import net.matrixcreations.libraries.MatrixColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static io.github.NoOne.nMLItems.ItemType.CROP;
import static io.github.NoOne.nMLItems.ItemType.SEED;

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

    public static ItemStack jadeFlowerBundle(int level, double stars, int amount, boolean displayItem) {
        ItemStack jadeFlowerBundle = new ItemStack(Material.PLAYER_HEAD, amount);

        setCropKeys(jadeFlowerBundle, CropType.JADE_FLOWER_BUSH, level, stars);

        SkullMeta meta = (SkullMeta) jadeFlowerBundle.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), null);
        PlayerTextures textures = playerProfile.getTextures();
        String levelLine = "§8Lv. " + level + " Crop";
        String starLine = "§6 < " + MaterialStars.getMaterialStarsEmoji(stars) + " >";

        pdc.set(ItemSystem.makeItemTypeKey(SEED), PersistentDataType.INTEGER, 1);
        pdc.set(ItemSystem.getSeedKey(), PersistentDataType.STRING, SeedType.getSeedTypeString(SeedType.SUGAR_CANE));

        try {
            URI uri = URI.create("https://textures.minecraft.net/texture/a9870df22db90e2411517f862522539defa0bbe30e41f54aa18d50393234d60");
            textures.setSkin(uri.toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (displayItem) {
            levelLine = "§8Lv. §kX §r§8Crop";
            starLine = "§6 < §kaaaaa §r§6>";
        }

        playerProfile.setTextures(textures);
        meta.setDisplayName(MatrixColorAPI.process("<SOLID:#00A86B>Jade Flower Bush"));
        meta.setLore(List.of(
                levelLine,
                "",
                starLine
        ));
        meta.setOwnerProfile(playerProfile);
        jadeFlowerBundle.setItemMeta(meta);

        return jadeFlowerBundle;
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
