package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.item.custom.Slingshot;
import com.ricky.chocolatemod.item.custom.TeleportItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return; // ENDフェーズのみ処理
        Player player = event.player;

        if (!player.level().isClientSide && !player.isCreative()) { // サーバーサイドのみ処理
            boolean hasXPItem = false;
            boolean hasSling = false;
            boolean hasStaff = false;

            // プレイヤーのインベントリをチェック
            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof TeleportItem) {
                    hasXPItem = true;
                    break;
                }
            }
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (hasXPItem && !player.getPersistentData().getBoolean("gold_event")){
                player.getPersistentData().putBoolean("gold_event", true);
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6ゴールドミッション開始！")));
                serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e世界を金ブロックで埋めよ！")));
            }


            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof Slingshot) {
                    hasSling = true;
                    break;
                }
            }
            if (hasSling && !player.getPersistentData().getBoolean("shooting_event")){
                player.getPersistentData().putBoolean("shooting_event", true);
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6的あてミッション開始！")));
                serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e8個の的に弾をあてよ！")));
            }
        }
    }
}