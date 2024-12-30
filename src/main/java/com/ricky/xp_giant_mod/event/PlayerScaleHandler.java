package com.ricky.xp_giant_mod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerScaleHandler {

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        float scale = 3; // 例えば0.5倍のサイズに縮小
        PoseStack poseStack = event.getPoseStack();

        // スケーリング処理
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
    }

    @SubscribeEvent
    public static void onPlayerRenderPost(RenderPlayerEvent.Post event) {
        // ポーズスタックを元に戻す
        event.getPoseStack().popPose();
    }
}
