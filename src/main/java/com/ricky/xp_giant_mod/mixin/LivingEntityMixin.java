package com.ricky.xp_giant_mod.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void modifyJumpPower(CallbackInfoReturnable<Float> cir) {
        if ((Object)this instanceof Player player){
            int experienceLevel = player.experienceLevel;
            // スケールを経験値レベルに応じて設定
            float scale;//TODO scale
            if (experienceLevel <= 5) {
                scale = 1.0f;
            } else if (experienceLevel <= 10) {
                scale = 2.5f;
            } else if (experienceLevel <= 20) {
                scale = 5.0f;
            } else {
                scale = 7.5f; // レベル20を超える場合は任意で設定（例: 7.5）
            }

            // EntityMixinを介してgetBlockJumpFactorを呼び出す
            float blockJumpFactor = ((EntityMixin) this).invokeGetBlockJumpFactor();

            // LivingEntity独自のgetJumpBoostPowerはそのまま利用可能
            LivingEntity entity = (LivingEntity) (Object) this;
            float jumpBoostPower = entity.getJumpBoostPower();

            // カスタマイズしたジャンプ力の計算
            float modified = 0.42F * blockJumpFactor * scale + jumpBoostPower;
            cir.setReturnValue(modified);
        }
    }
}
