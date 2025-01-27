package com.ricky.chocolatemod.entity.spawn;

import com.mojang.serialization.Codec;
import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpawnModifier implements BiomeModifier {

    public static final Codec<SpawnModifier> CODEC = Codec.unit(SpawnModifier::new);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        SpawnData.onBiome(biome, builder); // ここで SpawnData を呼び出す
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    // 登録用の DeferredRegister
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ChocolateMod.MOD_ID);

    public static final RegistryObject<Codec<SpawnModifier>> SPAWN_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register("spawn_modifier", () -> CODEC);
}
