package com.ricky.xp_giant_mod.mixin;

import com.ricky.xp_giant_mod.ScaleManager;
import com.ricky.xp_giant_mod.event.ChargeJumpHandler;
import net.minecraft.world.damagesource.DamageSource;
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
                chargeJump = 4;
                System.out.println("Super Jump Detected!");
                ChargeJumpHandler.setSuperJumping(true);
            }
            float modified = 0.42F * blockJumpFactor *chargeJump + jumpBoostPower + scale/20;
            cir.setReturnValue(modified);
        }
    }
    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void modifyFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            // スーパージャンプ中の場合、落下ダメージを無効化
            if (ChargeJumpHandler.isSuperJumping) {
                System.out.println("Super jump detected, canceling fall damage.");
                cir.setReturnValue(false); // 落下ダメージを発生させない
                cir.cancel(); // デフォルト処理をスキップ
                ChargeJumpHandler.landing(player);
                return;
            }

            // 通常の落下ダメージ処理
            float scale = ScaleManager.getScaleForPlayer(player);
            float adjustedFallDistance = pFallDistance - (3.0F + scale / 3.0F); // 高さの調整

            if (adjustedFallDistance > 0) {
                float damage = adjustedFallDistance * pMultiplier * (1.0F + scale / 10.0F);
                player.hurt(player.damageSources().fall(), damage); // カスタムダメージを適用
                cir.setReturnValue(true); // 落下ダメージを適用
                cir.cancel(); // デフォルト処理をスキップ
            } else {
                cir.setReturnValue(false); // ダメージなし
                cir.cancel(); // デフォルト処理をスキップ
            }
        }
    }
}
