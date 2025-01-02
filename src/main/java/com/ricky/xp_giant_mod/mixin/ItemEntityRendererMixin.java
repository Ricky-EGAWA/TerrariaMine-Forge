package com.ricky.xp_giant_mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.ScaleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
        Player player = Minecraft.getInstance().player;
        // 共通クラスを使用してスケールを取得
        float scale = ScaleManager.getScaleForPlayer(player)*2;
        pPoseStack.scale(scale, scale, scale);
        // 影の大きさをスケールに合わせて変更
        this.shadowRadius = 0.15F * scale;
    }
}

