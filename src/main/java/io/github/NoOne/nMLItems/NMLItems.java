package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.GenerateArmorCommand;
import io.github.NoOne.nMLItems.commands.GenerateQuiverCommand;
import io.github.NoOne.nMLItems.commands.GenerateShieldCommand;
import io.github.NoOne.nMLItems.itemDictionary.WeaponGenerator;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private ItemSystem itemSystem;
    private SkillSetManager skillSetManager;
    private WeaponGenerator weaponGenerator;

    @Override
    public void onEnable() {
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();
        itemSystem = new ItemSystem(this);
        weaponGenerator = new WeaponGenerator(this);

        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateShield").setExecutor(new GenerateShieldCommand());
        getCommand("generateQuiver").setExecutor(new GenerateQuiverCommand());
    }

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
    }

    public ItemSystem getItemSystem() {
        return itemSystem;
    }

    public WeaponGenerator getWeaponGenerator() {
        return weaponGenerator;
    }
}
