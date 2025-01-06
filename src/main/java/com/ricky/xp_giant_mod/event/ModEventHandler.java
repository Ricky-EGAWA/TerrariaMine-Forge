package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "xp_giant_mod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {
    @SubscribeEvent
    public static void onDragonDeath(LivingDropsEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            // カスタムアイテムの追加
            ItemEntity customDrop = new ItemEntity(
                    event.getEntity().level(),
                    event.getEntity().getX(),
                    event.getEntity().getY(),
                    event.getEntity().getZ(),
                    new ItemStack(ModItems.XP.get(), 1)
            );
            event.getDrops().add(customDrop);
        }
    }
}

