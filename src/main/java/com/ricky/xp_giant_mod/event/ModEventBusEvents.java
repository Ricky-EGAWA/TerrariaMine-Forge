package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.entity.ModEntities;
import com.ricky.xp_giant_mod.entity.custom.GiantMummy;
import com.ricky.xp_giant_mod.entity.custom.GiantRavager;
import com.ricky.xp_giant_mod.entity.custom.GiantVindicator;
import com.ricky.xp_giant_mod.entity.custom.GiantZombie;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GIANT_ZOMBIE.get(), GiantZombie.createAttributes().build());
        event.put(ModEntities.GIANT_VINDICATOR.get(), GiantVindicator.createAttributes().build());
        event.put(ModEntities.GIANT_RAVAGER.get(), GiantRavager.createAttributes().build());
        event.put(ModEntities.GIANT_MUMMY.get(), GiantMummy.createAttributes().build());
    }
}
