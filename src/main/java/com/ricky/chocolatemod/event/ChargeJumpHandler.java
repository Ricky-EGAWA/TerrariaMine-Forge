package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID)
public class ChargeJumpHandler {

    //jump meter
    private static ServerBossEvent bossBar = new ServerBossEvent(
            Component.translatable("bar.charge_jump"), // ボスバーの名前
            BossEvent.BossBarColor.BLUE, // ボスバーの色
            BossEvent.BossBarOverlay.PROGRESS // ボスバーのオーバーレイ
    );

    private static final int MAX_CHARGE_TIME = 20; // 最大チャージ時間（例：5秒）

    private static int chargeTime = 0;
    public static boolean canChargeJump = false;
    public static boolean isSuperJumping = false;

    private static boolean land = false;
    private static int landCount = 0;
    public static void setSuperJumping(boolean value) {
        isSuperJumping = value;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // サーバーサイドでのみ動作
        if (player.level().isClientSide || !(player instanceof ServerPlayer serverPlayer)) return;

        // プレイヤーがボスバーに追加されているかを確認
        boolean isPlayerInBossBar = bossBar.getPlayers().contains(serverPlayer);
//        System.out.println("super jump");

        // スニーク状態を検出
        if (serverPlayer.isCrouching() && serverPlayer.getInventory().getArmor(2).getItem() == ModItems.GOLDEN_ELYTRA.get()) {
//            System.out.println("charging");
            if (!isPlayerInBossBar && player.experienceLevel >= 1) {
                bossBar.addPlayer(serverPlayer); // プレイヤーにボスバーを表示
            }
            if(chargeTime<MAX_CHARGE_TIME){
                chargeTime++;
            }

            float progress = (float) chargeTime / MAX_CHARGE_TIME;
            bossBar.setProgress(progress);

            if (chargeTime >= MAX_CHARGE_TIME) {
                canChargeJump = true; // 最大チャージに達した
            }
        } else {
            // チャージが終了したらボスバーを非表示にし、チャージをリセット
            if (isPlayerInBossBar) {
                bossBar.removePlayer(serverPlayer);
            }
            chargeTime = 0;
            canChargeJump = false;
        }
        if (land){
            landCount++;
            if (landCount==20){
                isSuperJumping=false;
                landCount=0;
                land=false;
            }
        }
    }
}
