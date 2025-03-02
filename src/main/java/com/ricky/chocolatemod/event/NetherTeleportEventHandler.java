package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID)
public class NetherTeleportEventHandler {

    // プレイヤーごとのテレポートカウントダウン (UUID とカウントダウンのマップ)
    private static final Map<ServerPlayer, Integer> teleportCountdown = new HashMap<>();
    private static final int TELEPORT_DELAY_TICKS = 60; // 3秒 (20ticks * 3)
    private static boolean show = true;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            ServerPlayer player = (ServerPlayer) event.player;

            // インベントリ内に GOLD_MAN アイテムを持っているか確認
            boolean hasGoldMan = player.getInventory().items.stream()
                    .anyMatch(stack -> stack.getItem() == ModItems.HEALER.get());

            if (hasGoldMan && player.level().dimension() == Level.OVERWORLD && !player.getPersistentData().getBoolean("nether_event")) {
                player.getPersistentData().putBoolean("nether_event", true);
                if (show){
                    show = false;
                    player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6ネザーミッション開始！")));
                    player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§eネザーにテレポートします")));
                }
                // テレポートカウントを初期化または減少
                int remainingTicks = teleportCountdown.getOrDefault(player, TELEPORT_DELAY_TICKS) - 1;
                teleportCountdown.put(player, remainingTicks);


                // カウントがゼロに達したらエンドにテレポート
                if (remainingTicks <= 0) {
                    teleportPlayerToEnd(player);
                    teleportCountdown.remove(player); // カウントダウンをリセット
                }

            }
//            else {
//                // アイテムを持っていない、またはエンドにいない場合はカウントをリセット
//                if (teleportCountdown.containsKey(player)) {
//                    System.out.println("Teleport canceled.");
//                    teleportCountdown.remove(player);
//                }
//            }
        }
    }

    private static void teleportPlayerToEnd(ServerPlayer player) {
        System.out.println("Teleporting player to the Nether...");
        ServerLevel endWorld = player.getServer().getLevel(Level.NETHER);
        if (endWorld != null) {
            BlockPos spawnPos = new BlockPos(2, 52, 19);
            player.teleportTo(endWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player.getYRot(), player.getXRot());
        }
    }
}
