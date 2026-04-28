package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.*;
import io.github.NoOne.nMLItems.itemDictionary.Weapons;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private static NMLItems instance;
    private SkillSetManager skillSetManager;

    @Override
    public void onEnable() {
        instance = this;
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();

        ItemSystem.initialize();

        getCommand("generateItem").setExecutor(new GenerateItemCommand());
        getCommand("generateMaterial").setExecutor(new GenerateMaterialCommand());
        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateQuiver").setExecutor(new GenerateQuiverCommand());
        getCommand("generateHoe").setExecutor(new GenerateHoeCommand());
        getCommand("generateWeapon").setExecutor(new GenerateWeaponCommand());
    }

    public static NMLItems getInstance() {
        return instance;
    }

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
    }
}
