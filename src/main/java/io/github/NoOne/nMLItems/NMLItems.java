package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.*;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private static NMLItems instance;
    private SkillSetManager skillSetManager;
    private ItemSystem itemSystem;

    @Override
    public void onEnable() {
        instance = this;
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();
        itemSystem = new ItemSystem(this);

        getCommand("generateItem").setExecutor(new GenerateItemCommand());
        getCommand("generateCrop").setExecutor(new GenerateCropCommand());
        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateQuiver").setExecutor(new GenerateQuiverCommand());
        getCommand("generateHoe").setExecutor(new GenerateHoeCommand());
        getCommand("generateWeapon").setExecutor(new GenerateWeaponCommand());
        getCommand("generateIngredient").setExecutor(new GenerateIngredientCommand());
        getCommand("generateFood").setExecutor(new GenerateFoodCommand());
        getServer().getPluginManager().registerEvents(new ItemListener(this), this);
    }

    public static NMLItems getInstance() {
        return instance;
    }

    public ItemSystem getItemSystem() {
        return itemSystem;
    }

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
    }
}
