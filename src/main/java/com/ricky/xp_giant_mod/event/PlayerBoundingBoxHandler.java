package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBoundingBoxHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            float scale = 3f; // 当たり判定を縮小

            // 新しい当たり判定ボックスを設定
            AABB newBox = new AABB(
                    player.getX() - 0.3 * scale, player.getY(), player.getZ() - 0.3 * scale,
                    player.getX() + 0.3 * scale, player.getY() + 1.8 * scale, player.getZ() + 0.3 * scale
            );
            player.setBoundingBox(newBox);
        }
    }
}
