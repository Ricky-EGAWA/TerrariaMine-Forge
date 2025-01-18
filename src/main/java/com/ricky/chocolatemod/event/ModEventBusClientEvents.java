package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.ModModelLayers;
import com.ricky.chocolatemod.entity.projectile.HurricaneModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.HURRICANE_LAYER, HurricaneModel::createBodyLayer);
    }
}