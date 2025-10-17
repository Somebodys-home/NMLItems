package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.GenerateArmorCommand;
import io.github.NoOne.nMLItems.commands.GenerateQuiverCommand;
import io.github.NoOne.nMLItems.commands.GenerateShieldCommand;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private ItemSystem itemSystem;
    private static NMLPlayerStats nmlPlayerStats;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        itemSystem = new ItemSystem(this);

        Plugin plugin = Bukkit.getPluginManager().getPlugin("NMLPlayerStats");
        if (plugin instanceof NMLPlayerStats statsPlugin) {
            nmlPlayerStats = statsPlugin;
            profileManager = nmlPlayerStats.getProfileManager();
        }

        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateShield").setExecutor(new GenerateShieldCommand());
        getCommand("generateQuiver").setExecutor(new GenerateQuiverCommand());
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
}
