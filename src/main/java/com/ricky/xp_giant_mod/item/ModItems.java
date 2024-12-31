package com.ricky.xp_giant_mod.item;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.item.custom.XPItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, XPGiantMod.MOD_ID);
    public static final RegistryObject<Item> XP = ITEMS.register("xp",
            () -> new XPItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
