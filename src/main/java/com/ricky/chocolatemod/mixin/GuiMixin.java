package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.effect.ModEffects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    private final Minecraft minecraft = Minecraft.getInstance();
    private final ResourceLocation HEARTS_TEXTURE = new ResourceLocation("textures/gui/icons.png");

    @Inject(method = "renderHearts", at = @At("HEAD"), cancellable = true)
    private void modifyHeartRendering(GuiGraphics pGuiGraphics, Player pPlayer, int pX, int pY, int pHeight, int pOffsetHeartIndex, float pMaxHealth, int pCurrentHealth, int pDisplayHealth, int pAbsorptionAmount, boolean pRenderHighlight, CallbackInfo ci) {
        if (minecraft.player == null) return;

        Player player = minecraft.player;
        // エフェクトがプレイヤーに適用されているかチェック
        if (player.hasEffect(ModEffects.ALONG.get())) {
            // ALONGエフェクトが適用されているので、ハート描画をカスタマイズする
            MobEffectInstance effectInstance = player.getEffect(ModEffects.ALONG.get());
            if (effectInstance != null) {
                // カスタムハートの描画ロジックを挿入
                renderCustomHearts(pGuiGraphics, player, pX, pY);
                ci.cancel(); // 元のハート描画をキャンセル
            }
        }
    }
    private void renderCustomHearts(GuiGraphics guiGraphics, Player player, int xStart, int yStart) {
        int health = (int) Math.ceil(player.getHealth());
        int maxHealth = (int) Math.ceil(player.getMaxHealth());
        int heartsToRender = Math.min(health, maxHealth);

        int heartSize = 9; // ハートアイコンのサイズ

        for (int i = 0; i < heartsToRender / 2; i++) {
            // フルウィザーハートを描画
            guiGraphics.blit(HEARTS_TEXTURE, xStart + i * heartSize, yStart, 124, 0, heartSize, heartSize);
        }

        if (heartsToRender % 2 == 1) {
            // 半分のウィザーハートを描画
            guiGraphics.blit(HEARTS_TEXTURE, xStart + (heartsToRender / 2) * heartSize, yStart, 133, 0, heartSize, heartSize);
        }
    }
}
