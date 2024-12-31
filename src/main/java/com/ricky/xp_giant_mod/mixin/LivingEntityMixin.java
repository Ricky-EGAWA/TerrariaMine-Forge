package com.ricky.xp_giant_mod.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    float scale = 2f;

    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void modifyJumpPower(CallbackInfoReturnable<Float> cir) {
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
