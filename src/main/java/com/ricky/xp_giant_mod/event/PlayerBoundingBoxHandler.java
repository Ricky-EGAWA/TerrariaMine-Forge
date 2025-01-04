package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.ScaleManager;
import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBoundingBoxHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            // 共通クラスを使用してスケールを取得
            float scale = ScaleManager.getScaleForPlayer(player);

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
            // スケールに応じた属性を設定
            applyScaleAttributes(player, scale);
//            player.refreshDimensions();  // これを使うと目線の更新が行われるがヒットボックスがデフォルトのままになってしまう
        }
    }
    private static void applyScaleAttributes(Player player, float scale) {
        // 移動速度を変更
        player.getAttribute(Attributes.MOVEMENT_SPEED)
                .setBaseValue(0.1F + scale/20);


        // リーチ距離を変更
        player.getAttribute(ForgeMod.BLOCK_REACH.get())
                .setBaseValue(4.5F * scale); // デフォルトリーチ距離 4.5 にスケールを乗じる

        player.getAttribute(ForgeMod.ENTITY_REACH.get())
                .setBaseValue(3 * scale);

        int experienceLevel = player.experienceLevel;

        // 攻撃力
        if ( experienceLevel >= 50) {
            player.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(16);
        }
        if ( experienceLevel >= 80) {
            player.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(20);
        }
        if ( experienceLevel >= 90) {
            player.getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(140);
        }

        //体力
        if ( experienceLevel >= 40) {
            player.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(40);
        }
        if ( experienceLevel >= 80) {
            player.getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(100);
        }
    }
}
