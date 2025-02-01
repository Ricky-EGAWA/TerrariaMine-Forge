package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventSubscriber {
    
    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        List<Entity> affectedEntities = event.getAffectedEntities();

        // 爆発で消えないアイテムを除外
        affectedEntities.removeIf(entity -> entity instanceof ItemEntity itemEntity &&
                itemEntity.getItem().is(ModItems.VALENTINE_CHOCOLATE.get()));
    }
}
