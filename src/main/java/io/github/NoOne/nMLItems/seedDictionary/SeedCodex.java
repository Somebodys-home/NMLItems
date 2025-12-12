package io.github.NoOne.nMLItems.seedDictionary;

import io.github.NoOne.nMLItems.SeedType;
import org.bukkit.Material;

import java.util.HashMap;

public class SeedCodex {
    private HashMap<SeedType, SeedInformation> seedCodex;

    public SeedCodex() {
        seedCodex = new HashMap<>();

        seedCodex.put(SeedType.WHEAT_SEEDS, new SeedInformation(SeedType.WHEAT_SEEDS, Material.WHEAT));
    }

    public SeedInformation getSeedInformation(SeedType seedType) {
        return seedCodex.get(seedType);
    }
}
