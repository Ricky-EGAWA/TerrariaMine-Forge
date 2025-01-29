package com.ricky.chocolatemod.entity.monster;

import com.ricky.chocolatemod.effect.ModEffects;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CrowedWither extends WitherBoss {
    public CrowedWither(EntityType<? extends WitherBoss> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public boolean canBeAffected(MobEffectInstance pPotionEffect) {
        return pPotionEffect.getEffect() != ModEffects.ALONG.get() && super.canBeAffected(pPotionEffect);
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        ItemEntity itementity = this.spawnAtLocation(ModItems.VALENTINE_CHOCOLATE.get());
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

    }

}
