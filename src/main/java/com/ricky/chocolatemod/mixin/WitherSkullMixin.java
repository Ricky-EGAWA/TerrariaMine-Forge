package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkull.class)
public abstract class WitherSkullMixin {
    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    private void modifyOnHitEntity(EntityHitResult result, CallbackInfo ci) {
        Entity target = result.getEntity();
        Entity owner = ((WitherSkull) (Object) this).getOwner(); // キャストしてアクセス
        if (target instanceof LivingEntity livingTarget) {
            boolean damageApplied;

            // WitherSkullが持つlevelからdamageSourcesを取得
            if (((WitherSkull) (Object) this).level() != null) {
                var damageSources = ((WitherSkull) (Object) this).level().damageSources();

                if (owner instanceof LivingEntity livingOwner) {
                    damageApplied = target.hurt(damageSources.witherSkull((WitherSkull) (Object) this, livingOwner), 3);
                    if (damageApplied) {
                        // カスタムエフェクトを付与
                        livingTarget.addEffect(new MobEffectInstance(ModEffects.ALONG.get(), 200, 1), livingOwner);
                    }
                } else {
                    target.hurt(damageSources.magic(), 1.5f);
                }
            }

            ci.cancel(); // 元の処理をキャンセル
        }
    }
}

