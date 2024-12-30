package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBoundingBoxHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
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

            // 現在のポーズを取得
            Pose currentPose = player.getPose();

            // ポーズに応じた当たり判定ボックスを設定
            AABB newBox;
            switch (currentPose) {
                case SWIMMING:
                case FALL_FLYING:
                    // 泳ぎ/滑空時は横幅が狭く高さが低い
                    newBox = new AABB(
                            player.getX() - 0.3 * scale, player.getY(), player.getZ() - 0.3 * scale,
                            player.getX() + 0.3 * scale, player.getY() + 0.6 * scale, player.getZ() + 0.3 * scale
                    );
                    break;

                case CROUCHING:
                    // しゃがみ時は高さが低い
                    newBox = new AABB(
                            player.getX() - 0.3 * scale, player.getY(), player.getZ() - 0.3 * scale,
                            player.getX() + 0.3 * scale, player.getY() + 1.5 * scale, player.getZ() + 0.3 * scale
                    );
                    break;

                default:
                    // 通常時は標準の高さ
                    newBox = new AABB(
                            player.getX() - 0.3 * scale, player.getY(), player.getZ() - 0.3 * scale,
                            player.getX() + 0.3 * scale, player.getY() + 1.8 * scale, player.getZ() + 0.3 * scale
                    );
                    break;
            }

            // 新しい当たり判定ボックスを設定
            player.setBoundingBox(newBox);
        }
    }
}
