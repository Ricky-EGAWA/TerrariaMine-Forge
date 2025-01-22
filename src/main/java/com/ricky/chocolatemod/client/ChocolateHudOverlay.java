package com.ricky.chocolatemod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChocolateHudOverlay {
    private static final ResourceLocation ICONS = new ResourceLocation(ChocolateMod.MOD_ID, "textures/item/chocolate.png");
    private final Minecraft minecraft = Minecraft.getInstance();

    public ChocolateHudOverlay() {
        MinecraftForge.EVENT_BUS.register(this); // イベント登録
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        // 必要な情報を取得
        ChocolateCounter counter = ChocolateCounter.getInstance();
        int chocolateCount = counter.getChocolate();

        // 描画位置
        int x = 10; // 左上の x 座標
        int y = 10; // 左上の y 座標

        // アイコン描画
        RenderSystem.setShaderTexture(0, ICONS);
        GuiGraphics guiGraphics = event.getGuiGraphics();
        guiGraphics.blit(ICONS, x, y, 0, 0, 16, 16, 16, 16); // アイコンを描画

        // テキスト描画
        guiGraphics.drawString(minecraft.font, String.valueOf(chocolateCount), x + 20, y + 4, 0xFFFFFF, true); // チョコレート数を描画
    }
}
