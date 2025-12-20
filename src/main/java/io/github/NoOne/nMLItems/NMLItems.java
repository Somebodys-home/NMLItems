package io.github.NoOne.nMLItems;

import io.github.NoOne.nMLItems.commands.*;
import io.github.NoOne.nMLItems.itemDictionary.Weapons;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLItems extends JavaPlugin {
    private ItemSystem itemSystem;
    private SkillSetManager skillSetManager;
    private Weapons weapons;

    @Override
    public void onEnable() {
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();
        itemSystem = new ItemSystem(this);
        weapons = new Weapons(this);

        getCommand("generateItem").setExecutor(new GenerateItemCommand());
        getCommand("generateMaterial").setExecutor(new GenerateMaterialCommand());
        getCommand("generateArmor").setExecutor(new GenerateArmorCommand());
        getCommand("generateShield").setExecutor(new GenerateShieldCommand());
        getCommand("generateQuiver").setExecutor(new GenerateQuiverCommand());
        getCommand("generateHoe").setExecutor(new GenerateHoeCommand());
    }

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
    }

    public ItemSystem getItemSystem() {
        return itemSystem;
    }

    public Weapons getWeaponGenerator() {
        return weapons;
    }
}
