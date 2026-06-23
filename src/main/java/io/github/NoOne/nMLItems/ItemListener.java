package io.github.NoOne.nMLItems;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {
    private NMLItems nmlItems;

    public ItemListener(NMLItems nmlItems) {
        this.nmlItems = nmlItems;
    }

    @EventHandler
    public void dontUseItems(PlayerInteractEvent event) {
        if (nmlItems.getItemSystem().getItemType(event.getItem()) != null) {
            event.setCancelled(true);
        }
    }
}
