package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.item.custom.XPItem;
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

        if (!player.level().isClientSide) { // サーバーサイドのみ処理
            boolean hasXPItem = false;

            // プレイヤーのインベントリをチェック
            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof XPItem) {
                    hasXPItem = true;
                    break;
                }
            }

            if (hasXPItem) {
                // RadarPickaxe をインベントリから削除
                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack stack = player.getInventory().items.get(i);
                    if (stack.getItem() instanceof XPItem) {
                        player.getInventory().removeItem(i, stack.getCount());
                        // プレイヤーのレベルを10上げる
                        player.giveExperienceLevels(10);
                        break;
                    }
                }
            }
        }
    }
}