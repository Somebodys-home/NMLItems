package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private NMLItems instance;
    private ItemSystem itemSystem;
    private static NMLPlayerStats nmlPlayerStats;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        instance = this;
        itemSystem = new ItemSystem(this);

        Plugin plugin = Bukkit.getPluginManager().getPlugin("NMLPlayerStats");
        if (plugin instanceof NMLPlayerStats statsPlugin) {
            nmlPlayerStats = statsPlugin;
            profileManager = nmlPlayerStats.getProfileManager();
        }
    }

    public ItemSystem getItemSystem() {
        return itemSystem;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public static NMLPlayerStats getNmlPlayerStats() {
        return nmlPlayerStats;
    }

    public NMLItems getInstance() {
        return instance;
    }
}
