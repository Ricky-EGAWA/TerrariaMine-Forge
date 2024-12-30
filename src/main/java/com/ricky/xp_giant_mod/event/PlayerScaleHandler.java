package com.ricky.xp_giant_mod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerScaleHandler {

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity(); // Renderされるプレイヤーを取得
        int experienceLevel = player.experienceLevel; // プレイヤーの経験値レベルを取得

        // スケールを経験値レベルに応じて設定
        float scale;
        if (experienceLevel <= 5) {
            scale = 1.0f;
        } else if (experienceLevel <= 10) {
            scale = 2.5f;
        } else if (experienceLevel <= 20) {
            scale = 5.0f;
        } else {
            scale = 7.5f; // レベル20を超える場合のスケール
        }

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
