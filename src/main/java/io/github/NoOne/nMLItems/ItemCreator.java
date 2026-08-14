package io.github.NoOne.nMLItems;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class ItemCreator {
    public static ItemStack createItem(Material material, int amount, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);
        return item;
    }


    // used in other plugins
     public static ItemStack createSkull(String base64) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());

            playerProfile.setProperty(new ProfileProperty("textures", base64));
            meta.setPlayerProfile(playerProfile);
            skull.setItemMeta(meta);
            return skull;
        }

    public static ItemStack createBackoutButton() {
        return createItem(Material.BARRIER, "§c§l<- §r§cBack");
    }

    public static ItemStack createMenuBorder() {
        return createItem(Material.BLACK_STAINED_GLASS_PANE, "§0§l_");
    }
}
