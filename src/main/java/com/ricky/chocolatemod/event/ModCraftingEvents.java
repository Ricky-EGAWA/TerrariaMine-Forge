package com.ricky.chocolatemod.event;

import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCraftingEvents {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        // サーバーサイドでのみ処理を行う
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        // クラフトされたアイテムを取得
        ItemStack craftedItem = event.getCrafting();

        // クラフトされたアイテムが "CHOCOLATE" かどうかを確認
        if (craftedItem.getItem() == ModItems.CHOCOLATE.get()) {

            // 初回クラフト時にメッセージを表示
            if (!player.getPersistentData().getBoolean("crafted_chocolate_message_shown")) {
                player.getPersistentData().putBoolean("crafted_chocolate_message_shown", true);

                // テレポートアイテムを付与
                player.getInventory().add(new ItemStack(ModItems.TELEPORT_ITEM.get()));

                // **画面中央にタイトルを表示**
                player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6チョコミッション開始！")));
                player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§eチョコの冒険が今始まる...")));
            }
        }
    }
}
