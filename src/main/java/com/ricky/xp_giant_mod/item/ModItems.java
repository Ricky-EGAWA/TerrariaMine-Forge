package com.ricky.xp_giant_mod.item;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.entity.ModEntities;
import com.ricky.xp_giant_mod.item.custom.MediumXPItem;
import com.ricky.xp_giant_mod.item.custom.SmallXPItem;
import com.ricky.xp_giant_mod.item.custom.XPItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, XPGiantMod.MOD_ID);
    public static final RegistryObject<Item> XP = ITEMS.register("xp",
            () -> new XPItem(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_XP = ITEMS.register("small_xp",
            () -> new SmallXPItem(new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_XP = ITEMS.register("medium_xp",
            () -> new MediumXPItem(new Item.Properties()));

    public static final RegistryObject<Item> GIANT_RAVAGER_SPAWN_EGG = ITEMS.register("giant_ravager_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GIANT_RAVAGER, 0x8A3324, 0x000000,
                    new Item.Properties()));
    public static final RegistryObject<Item> GIANT_ZOMBIE_SPAWN_EGG = ITEMS.register("giant_zombie_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GIANT_ZOMBIE, 0x8A3324, 0x00FF00,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
