package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.item.custom.TeleportItem;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class NetherEvent {

    // スケジューラーを作成 (1スレッドで非同期タスクを実行)
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        // ピグリンブルートが死亡した場合
        if (entity instanceof PiglinBrute) {
            Entity source = event.getSource().getEntity();

            // プレイヤーが討伐した場合
            if (source instanceof ServerPlayer player) {
                ChocolateCounter.getInstance().netherEvent(); // netherEventメソッドの呼び出し
                player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6ファイターピグリンを討伐！")));
                player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e5秒後に現世に戻ります")));

                // 5秒後にテレポートを実行
                scheduler.schedule(() -> teleportPlayerToOverworld(player), 5, TimeUnit.SECONDS);
            }
        }
    }

    // プレイヤーを現世の初期リスポーン地点にテレポートするメソッド
    private static void teleportPlayerToOverworld(ServerPlayer player) {
        ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
        System.out.println("finish nether event");

        if (overworld != null) {
            BlockPos spawnPos = overworld.getSharedSpawnPos(); // ワールドの初期スポーン地点を取得
//            BlockPos spawnPos = TeleportItem.getInstance().rePos;

            // 現世にテレポート
            player.teleportTo(overworld, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());

            // テレポート後のメッセージ
//            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§a現世に戻りました！")));
//            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e" + spawnPos.getX() + ", " + spawnPos.getY() + ", " + spawnPos.getZ())));
        }
    }
}
