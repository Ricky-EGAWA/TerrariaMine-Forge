package com.ricky.chocolatemod.item;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChocolateMod.MOD_ID);
    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(ModFoods.CHOCOLATE)));
    public static final RegistryObject<Item> ORE_PICKAXE = ITEMS.register("ore_pickaxe",
            OrePickaxe::new);
    public static final RegistryObject<Item> CHOCOLATE_SWORD = ITEMS.register("chocolate_sword",
            ChocolateSword::new);
    public static final RegistryObject<Item> CHOCOLATE_SLINGSHOT = ITEMS.register("chocolate_slingshot",
            Slingshot::new);
    public static final RegistryObject<Item> CHOCOLATE_BOMB = ITEMS.register("chocolate_bomb",
            () -> new ChocolateBomb(new Item.Properties()));
    public static final RegistryObject<Item> HEALER = ITEMS.register("healer",
            () -> new Healer(new Item.Properties()));
    public static final RegistryObject<Item> HURRICANE = ITEMS.register("hurricane",
            () -> new Hurricane(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
