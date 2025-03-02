package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.entity.monster.fighter.FighterEntity;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class FighterDeathEvent {

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        System.out.println("dead");

        // FighterEntityが死亡した場合
        if (entity instanceof FighterEntity) {
            System.out.println("fighter dead");
            ServerLevel level = (ServerLevel) entity.level();

            // エンティティの周囲10ブロック以内のプレイヤーを検索
            double radius = 30.0; // 検索範囲（10ブロック）
            AABB searchArea = new AABB(
                    entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius,
                    entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius
            );

            // 指定範囲内のプレイヤーを取得
            List<ServerPlayer> nearbyPlayers = level.getEntitiesOfClass(ServerPlayer.class, searchArea);

            // 近くにプレイヤーがいる場合、最初の1人に対して処理を適用
            if (!nearbyPlayers.isEmpty()) {
                ServerPlayer player = nearbyPlayers.get(0); // 最初のプレイヤーを取得

                ChocolateCounter.getInstance().netherEvent(); // 任意のカウントアップやイベント処理
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

            // 現世にテレポート
            player.teleportTo(overworld, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }
    }
}
