package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.*;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.SaveSkillsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private static NMLItems instance;
    private SaveSkillsManager saveSkillsManager;

    @Override
    public void onEnable() {
        instance = this;
        saveSkillsManager = JavaPlugin.getPlugin(NMLSkills.class).getSaveSkillsManager();

        getCommand("generateItem").setExecutor(new GenerateItemCommand());
        getCommand("generateCrop").setExecutor(new GenerateCropCommand());
        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateOffhand").setExecutor(new GenerateOffhandCommand());
        getCommand("generateHoe").setExecutor(new GenerateHoeCommand());
        getCommand("generateWeapon").setExecutor(new GenerateWeaponCommand());
        getCommand("generateIngredient").setExecutor(new GenerateIngredientCommand());
        getCommand("generateFood").setExecutor(new GenerateFoodCommand());
    }

    public static NMLItems getInstance() {
        return instance;
    }

    public SaveSkillsManager getSaveSkillsManager() {
        return saveSkillsManager;
    }
}
