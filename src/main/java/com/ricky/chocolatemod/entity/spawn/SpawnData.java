package com.ricky.chocolatemod.entity.spawn;

import com.ricky.chocolatemod.entity.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class SpawnData {

    public static void onBiome(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builderIn) {
        MobSpawnSettingsBuilder builder = builderIn.getMobSpawnSettings();


        builder.addSpawn  (MobCategory.MONSTER,new SpawnerData(ModEntities.SUGAR_SLIME.get(), 5, 1, 1));
        builder.addSpawn  (MobCategory.MONSTER,new SpawnerData(ModEntities.CROWED_MONSTER.get(), 15, 1, 1));

    }
}
