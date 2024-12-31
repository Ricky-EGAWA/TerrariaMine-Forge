package com.ricky.xp_giant_mod.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "xp_giant_mod")
public class ChargeJumpHandler {

    private static final int MAX_CHARGE_TIME = 100; // 最大チャージ時間（例：5秒）
    private static final float MAX_JUMP_POWER = 3.0f; // 最大ジャンプ力
    private static final float MIN_JUMP_POWER = 1.5f; // 最小ジャンプ力
    private static final float EXPLOSION_RADIUS = 5.0f; // 爆風の範囲

    private static int chargeTime = 0;
    public static boolean canChargeJump = false;
    public static boolean isSuperJumping = false;
    public static boolean finish = false;
    public static void setSuperJumping(boolean value) {
        isSuperJumping = value;
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player && isSuperJumping) {
            if (finish){
                isSuperJumping = false; // 着地後にスーパージャンプを終了
                finish = false;
                //爆風
//                player.level().explode(null, player.getX(), player.getY(), player.getZ(), EXPLOSION_RADIUS, false, Level.ExplosionInteraction.NONE);
            }
            System.out.println("Super Jump fall detected!");
            if (player.fallDistance > 0) {
                event.setCanceled(true); // 落下ダメージ無効化
                player.fallDistance = 0.0f; // 落下ダメージ無効化後、落下距離をリセット
                if (player.onGround()){
                    finish = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        System.out.println(isSuperJumping);//debug


        // クライアントサイドのみで動作
        if (event.phase != TickEvent.Phase.START || !(player instanceof LocalPlayer)) return;

        LocalPlayer localPlayer = (LocalPlayer) player;

        // スニーク状態を検出
        if (localPlayer.isCrouching()) { // スニーク中のチャージ
            chargeTime++;
            if (chargeTime >= MAX_CHARGE_TIME) {
                canChargeJump = true; // 最大チャージに達した
            }
        } else {
            chargeTime = 0;
            canChargeJump = false;
        }
    }


}
