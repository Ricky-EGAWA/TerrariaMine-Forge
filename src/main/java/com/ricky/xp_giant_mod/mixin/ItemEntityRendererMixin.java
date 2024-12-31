package com.ricky.xp_giant_mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {
    @Mutable
    private float shadowRadius;
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(ItemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        // スケールを適用
        float scale = 3.0F; // アイテムの拡大率を指定//TODO scale
        pPoseStack.scale(scale, scale, scale);
        // 影の大きさをスケールに合わせて変更
        this.shadowRadius = 0.15F * scale;
    }
}

