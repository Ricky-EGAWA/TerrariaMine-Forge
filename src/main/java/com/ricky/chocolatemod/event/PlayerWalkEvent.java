package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerWalkEvent {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        boolean hasTeleportItem = false;
        for (ItemStack stack : event.player.getInventory().items) {
            if (stack.getItem() == ModItems.TELEPORT_ITEM.get()) {
                hasTeleportItem = true;
                break;
            }
        }
        if (!hasTeleportItem) {
            return;
        }
        // サーバー側のみ処理
        if (!event.player.level().isClientSide && event.phase == TickEvent.Phase.END) {
            if (event.player.getInventory().getArmor(2).getItem() == ModItems.GOLDEN_ELYTRA.get()) {
                BlockPos position = event.player.blockPosition();
                int explosionRadius = 3;
                for (int x = -explosionRadius; x <= explosionRadius; x++) {
                    for (int y = -explosionRadius; y <= explosionRadius; y++) {
                        for (int z = -explosionRadius; z <= explosionRadius; z++) {
                            BlockPos nearbyPos = position.offset(x, y, z);
                            ChangeChocolate.change(event.player.level(), nearbyPos, false);
                        }
                    }
                }
            } else if (event.player.getPersistentData().getBoolean("ate_golden_apple")) {
                BlockPos position = event.player.blockPosition();
                int explosionRadius = 2;
                for (int x = -explosionRadius; x <= explosionRadius; x++) {
                    for (int y = -explosionRadius; y <= explosionRadius; y++) {
                        for (int z = -explosionRadius; z <= explosionRadius; z++) {
                            BlockPos nearbyPos = position.offset(x, y, z);
                            ChangeChocolate.change(event.player.level(), nearbyPos, false);
                        }
                    }
                }
            }else{ //黄金リンゴを食べる前
                // プレイヤーの足元の位置を取得
                BlockPos position = event.player.blockPosition().below();
                ChangeChocolate.change(event.player.level(), position, false);
            }
        }
    }

    // イベント登録
    public static void register() {
        MinecraftForge.EVENT_BUS.register(PlayerWalkEvent.class);
    }
}
