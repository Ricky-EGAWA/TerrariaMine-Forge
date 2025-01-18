package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedMonster;
import com.ricky.chocolatemod.entity.monster.CrowedWither;
import com.ricky.chocolatemod.entity.monster.SugarSlime;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUGAR_SLIME.get(), SugarSlime.createAttributes().build());
        event.put(ModEntities.CROWED_MONSTER.get(), CrowedMonster.createAttributes().build());
        event.put(ModEntities.CROWED_WITHER.get(), CrowedWither.createAttributes().build());
    }
}