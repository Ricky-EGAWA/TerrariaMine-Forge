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

    public static final RegistryObject<Item> GOLDEN_EGG = ITEMS.register("golden_egg",
            () -> new GoldenEgg(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SLINGSHOT = ITEMS.register("golden_slingshot",
            Slingshot::new);
    public static final RegistryObject<Item> GOLDEN_EATER = ITEMS.register("golden_eater",
            () -> new Eater());
    public static final RegistryObject<Item> GOLDEN_APPLE = ITEMS.register("golden_apple",
            () -> new GoldenApple());
    public static final RegistryObject<Item> GOLDEN_ELYTRA = ITEMS.register("golden_elytra",
            CustomElytraItem::new);
    public static final RegistryObject<Item> GOLDEN_ROCKET = ITEMS.register("golden_rocket",
            () -> new CustomRocketItem(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_MAN = ITEMS.register("gold_man",
            () -> new GoldMan(new Item.Properties()));



//    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
//            () -> new Item(new Item.Properties().food(ModFoods.CHOCOLATE)));
//    public static final RegistryObject<Item> VALENTINE_CHOCOLATE = ITEMS.register("valentine_chocolate",
//            () -> new Item(new Item.Properties().food(ModFoods.CHOCOLATE)));
    public static final RegistryObject<Item> ORE_PICKAXE = ITEMS.register("ore_pickaxe",
            OrePickaxe::new);
    public static final RegistryObject<Item> CHOCOLATE_SWORD = ITEMS.register("chocolate_sword",
            ChocolateSword::new);
    public static final RegistryObject<Item> CUPID = ITEMS.register("cupid",
            Cupid::new);
    public static final RegistryObject<Item> HEALER = ITEMS.register("gold_staff",
            () -> new GoldStaff(new Item.Properties()));
    public static final RegistryObject<Item> HURRICANE = ITEMS.register("hurricane",
            () -> new Hurricane(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC = ITEMS.register("magic",
            () -> new Magic(new Item.Properties()));

    public static final RegistryObject<Item> TELEPORT_ITEM = ITEMS.register("teleport",
            () -> new TeleportItem(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
