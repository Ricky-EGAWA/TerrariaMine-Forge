package com.ricky.xp_giant_mod.mixin;

import com.ricky.xp_giant_mod.ScaleManager;
import com.ricky.xp_giant_mod.event.ChargeJumpHandler;
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
            // 共通クラスを使用してスケールを取得
            float scale = ScaleManager.getScaleForPlayer(player);

            // EntityMixinを介してgetBlockJumpFactorを呼び出す
            float blockJumpFactor = ((EntityMixin) this).invokeGetBlockJumpFactor();

            // LivingEntity独自のgetJumpBoostPowerはそのまま利用可能
            LivingEntity entity = (LivingEntity) (Object) this;
            float jumpBoostPower = entity.getJumpBoostPower();

            // カスタマイズしたジャンプ力の計算
            int chargeJump = 1;
            if (ChargeJumpHandler.canChargeJump){
                chargeJump = 10;
                System.out.println("Super Jump Detected!");
                ChargeJumpHandler.setSuperJumping(true);
            }
            float modified = 0.42F * blockJumpFactor * scale *chargeJump + jumpBoostPower;
            cir.setReturnValue(modified);
        }
    }
}
