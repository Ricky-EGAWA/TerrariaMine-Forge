package com.ricky.chocolatemod.entity.monster;

import com.ricky.chocolatemod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;

public class CrowedWither extends WitherBoss {
    public CrowedWither(EntityType<? extends WitherBoss> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public boolean canBeAffected(MobEffectInstance pPotionEffect) {
        return pPotionEffect.getEffect() != ModEffects.SUGAR_RUSH.get() && super.canBeAffected(pPotionEffect);
    }
}
