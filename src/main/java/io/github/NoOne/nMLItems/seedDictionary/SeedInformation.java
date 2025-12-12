package io.github.NoOne.nMLItems.seedDictionary;

import io.github.NoOne.nMLItems.itemClassifiers.SeedType;
import org.bukkit.Material;

public class SeedInformation {
    private SeedType seedType;
    private Material blockMaterial;

    public SeedInformation(SeedType seedType, Material blockMaterial) {
        this.seedType = seedType;
        this.blockMaterial = blockMaterial;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }
}
