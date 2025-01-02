package com.ricky.xp_giant_mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.event.ChargeJumpHandler;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperRenderer.class)
public abstract class CreeperMixin {
    @Inject(method = "scale", at = @At("HEAD"), cancellable = true)
    private void scale(Creeper pLivingEntity, PoseStack pPoseStack, float pPartialTickTime, CallbackInfo ci) {
        // プレイヤーがスーパージャンプをした後、着地地点の周囲5ブロック内にいるか確認
        double dx = pLivingEntity.getX() - ChargeJumpHandler.landingX;
        double dy = pLivingEntity.getY() - ChargeJumpHandler.landingY;
        double dz = pLivingEntity.getZ() - ChargeJumpHandler.landingZ;

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        // 5ブロック以内にいる場合、または潰れた状態が維持されている場合はレンダリング変更
        if (distance <= 15.0 || ChargeJumpHandler.isCreeperFlattened(pLivingEntity)) {
            // レンダリングのスケールを変更
            float f = pLivingEntity.getSwelling(pPartialTickTime);
            float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f2 = (1.0F + f * 0.4F) * f1;
            float f3 = (1.0F + f * 0.1F) / f1;

            // 着地地点近くにいる場合はy軸を圧縮
            pPoseStack.scale(f2, f3 / 8, f2); // 例: 高さを8分の1にする
            ci.cancel(); // 元の処理をキャンセル
        }
    }
}


