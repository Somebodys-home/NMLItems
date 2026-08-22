package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.enums.ItemType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void dontUseItems(PlayerInteractEvent event) {
        if (ItemSystem.getItemType(event.getItem()) != null && ItemSystem.getItemType(event.getItem()) != ItemType.FOOD) {
            event.setCancelled(true);
        }
    }
}
