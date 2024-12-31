package com.ricky.xp_giant_mod.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;

public class LandingHandler {
    private static boolean wasOnGround = false; // 前回の状態を保持

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        boolean isOnGround = player.onGround();

    }
}
