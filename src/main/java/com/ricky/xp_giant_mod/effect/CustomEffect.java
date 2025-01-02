package com.ricky.xp_giant_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class CustomEffect extends MobEffect {
    public CustomEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
//        if (!entity.level().isClientSide) {
//            updateBoundingBox(entity);
//        }
    }

//    private void updateBoundingBox(LivingEntity entity) {
//        var box = entity.getBoundingBox();
//        double newHeight = box.getYsize() / 8.0;
//        AABB newBox = new AABB(box.minX, box.minY, box.minZ, box.maxX, box.minY + newHeight, box.maxZ);
//        entity.setBoundingBox(newBox);
//        entity.refreshDimensions();
//    }
//
//    @Override
//    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
//        super.removeAttributeModifiers(entity, attributeMap, amplifier);
//        entity.setBoundingBox(entity.getType().getDimensions().makeBoundingBox(entity.position()));
//        entity.refreshDimensions();
//    }



    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 毎秒（20ティックごと）にエフェクトを適用
    }
}
