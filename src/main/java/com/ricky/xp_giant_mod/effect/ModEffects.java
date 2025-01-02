package com.ricky.xp_giant_mod.effect;

import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, XPGiantMod.MOD_ID);

    public static final RegistryObject<MobEffect> CUSTOM_EFFECT = EFFECTS.register("custom_effect",
            () -> new CustomEffect(MobEffectCategory.BENEFICIAL, 0xFF0000)); // 赤いエフェクト
}
