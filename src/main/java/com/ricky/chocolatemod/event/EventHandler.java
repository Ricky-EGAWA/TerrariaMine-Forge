package com.ricky.chocolatemod.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerEnterEnd(EntityJoinLevelEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel){
            removeAllEndCrystals(serverLevel);
        }
    }

    public static void removeAllEndCrystals(ServerLevel serverLevel) {
        if (serverLevel.dimension() == Level.END) {
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity instanceof EndCrystal) {
                    entity.kill();
                }
            }
        }
    }
}
