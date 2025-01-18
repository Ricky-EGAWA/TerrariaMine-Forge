package com.ricky.chocolatemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class AlongEffect extends MobEffect {
    public AlongEffect() {
        // MobEffectCategory.BENEFICIAL: プレイヤーにとって有益な効果
        // 0xFFFFFF: 効果の表示色
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    @Override
    public void applyEffectTick(net.minecraft.world.entity.LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 効果が発動する間隔を指定
        return duration % 20 == 0; // 1秒ごとに発動
    }
}