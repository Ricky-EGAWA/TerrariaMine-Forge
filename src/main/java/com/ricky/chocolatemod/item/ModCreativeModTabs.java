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
                        pOutput.accept(ModItems.ORE_PICKAXE.get());
                        pOutput.accept(ModItems.CHOCOLATE_SWORD.get());
                        pOutput.accept(ModItems.CHOCOLATE_SLINGSHOT.get());
                        pOutput.accept(ModBlocks.CHOCOLATE_BLOCK.get());
                        pOutput.accept(ModItems.CHOCOLATE_BOMB.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATE_MODE_TABS.register(eventBus);
    }
}
