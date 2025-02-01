package com.ricky.chocolatemod.item;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChocolateMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_ITEM_TAB = CREATE_MODE_TABS.register("mod_item_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.CHOCOLATE.get()))
                    .title(Component.translatable("creativetab.mod_item_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CHOCOLATE.get());
                        pOutput.accept(ModItems.VALENTINE_CHOCOLATE.get());
                        pOutput.accept(ModItems.ORE_PICKAXE.get());
                        pOutput.accept(ModItems.CHOCOLATE_SWORD.get());
                        pOutput.accept(ModItems.CHOCOLATE_SLINGSHOT.get());
                        pOutput.accept(ModItems.CUPID.get());
                        pOutput.accept(ModBlocks.CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.DIAMOND_CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.EMERALD_CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.GOLD_CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.IRON_CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.LAPIS_CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModItems.CHOCOLATE_BOMB.get());
                        pOutput.accept(ModItems.HEALER.get());
                        pOutput.accept(ModItems.HURRICANE.get());
                        pOutput.accept(ModItems.MAGIC.get());
                        pOutput.accept(ModItems.TELEPORT_ITEM.get());
                        pOutput.accept(ModBlocks.EXCHANGE_ORE_PICKAXE.get());
                        pOutput.accept(ModBlocks.EXCHANGE_HEALER.get());
                        pOutput.accept(ModBlocks.EXCHANGE_HURRICANE.get());
                        pOutput.accept(ModBlocks.EXCHANGE_BOMB.get());
                        pOutput.accept(ModBlocks.EXCHANGE_CUPID.get());
                        pOutput.accept(ModBlocks.EXCHANGE_SWORD.get());
                        pOutput.accept(ModBlocks.EXCHANGE_SLINGSHOT.get());
                        pOutput.accept(ModBlocks.EXCHANGE_MAGIC.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATE_MODE_TABS.register(eventBus);
    }
}
