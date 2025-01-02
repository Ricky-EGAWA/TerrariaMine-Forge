package com.ricky.xp_giant_mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.event.ChargeJumpHandler;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "scale", at = @At("HEAD"), cancellable = true)
    private void scale(T entity, PoseStack poseStack, float partialTickTime, CallbackInfo ci) {
        // モブが潰れた状態かを確認
        if (ChargeJumpHandler.isMobFlattened(entity)) {
            poseStack.scale(1.0F, 1F/8, 1.0F); // Y軸を圧縮して潰れた見た目に
            ci.cancel(); // 元のスケーリング処理をキャンセル
        }
    }
}
