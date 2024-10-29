package com.TBK.WitchArchive;

import com.TBK.WitchArchive.server.world.SpawnBiomeData;

public class DefaultBiomes {
    public static final SpawnBiomeData EMPTY = new SpawnBiomeData();

    public static final SpawnBiomeData HARPY = new SpawnBiomeData()
            .addBiomeEntry(SpawnBiomeData.BiomeEntryType.REGISTRY_NAME, false, "minecraft:stony_peaks", 0)
            .addBiomeEntry(SpawnBiomeData.BiomeEntryType.REGISTRY_NAME, false, "minecraft:frozen_peaks", 1)
            .addBiomeEntry(SpawnBiomeData.BiomeEntryType.REGISTRY_NAME, false, "minecraft:jagged_peaks", 2);


}
