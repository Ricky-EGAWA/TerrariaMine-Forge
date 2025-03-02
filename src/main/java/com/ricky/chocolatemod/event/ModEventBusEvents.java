package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.client.FoundParticle;
import com.ricky.chocolatemod.client.ModParticles;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedMonster;
import com.ricky.chocolatemod.entity.monster.CrowedWither;
import com.ricky.chocolatemod.entity.monster.SugarSlime;
import com.ricky.chocolatemod.entity.monster.fighter.FighterEntity;
import com.ricky.chocolatemod.entity.monster.goldman.GoldManEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
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
        event.put(ModEntities.GOLD_MAN.get(), GoldManEntity.createAttributes().build());
        event.put(ModEntities.FIGHTER.get(), FighterEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FOUND.get(),
                FoundParticle.Provider::new);
    }
}