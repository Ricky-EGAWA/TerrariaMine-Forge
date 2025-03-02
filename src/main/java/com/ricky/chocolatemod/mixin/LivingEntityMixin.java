package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.event.ChargeJumpHandler;
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
        if ((Object) this instanceof Player player) {

            // EntityMixinを介してgetBlockJumpFactorを呼び出す
            float blockJumpFactor = ((EntityMixin) this).invokeGetBlockJumpFactor();

            // LivingEntity独自のgetJumpBoostPowerはそのまま利用可能
            LivingEntity entity = (LivingEntity) (Object) this;
            float jumpBoostPower = entity.getJumpBoostPower();

            // カスタマイズしたジャンプ力の計算
            float chargeJump = 0;
            if (ChargeJumpHandler.canChargeJump) {
                chargeJump = 1.5f; //TODO 値調性
                ChargeJumpHandler.setSuperJumping(true);
            }
            float modified = 0.42F * blockJumpFactor + jumpBoostPower + chargeJump;
            cir.setReturnValue(modified);
        }
    }
}