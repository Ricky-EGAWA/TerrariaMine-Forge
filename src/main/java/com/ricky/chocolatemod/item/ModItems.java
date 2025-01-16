package com.ricky.chocolatemod.item;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.custom.ChocolateSword;
import com.ricky.chocolatemod.item.custom.OrePickaxe;
import com.ricky.chocolatemod.item.custom.Slingshot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChocolateMod.MOD_ID);
    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ORE_PICKAXE = ITEMS.register("ore_pickaxe",
            OrePickaxe::new);
    public static final RegistryObject<Item> CHOCOLATE_SWORD = ITEMS.register("chocolate_sword",
            ChocolateSword::new);
    public static final RegistryObject<Item> CHOCOLATE_SLINGSHOT = ITEMS.register("chocolate_slingshot",
            Slingshot::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
