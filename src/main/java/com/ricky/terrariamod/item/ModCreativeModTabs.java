package com.ricky.terrariamod.item;

import com.ricky.terrariamod.TerrariaMod;
import com.ricky.terrariamod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerrariaMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_ITEM_TAB = CREATE_MODE_TABS.register("mod_item_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.COBALT_INGOT.get()))
                    .title(Component.translatable("creativetab.mod_item_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COBALT_INGOT.get());
                        pOutput.accept(ModItems.COBALT_RAW.get());
                        pOutput.accept(ModBlocks.COBALT_ORE.get());
                        pOutput.accept(ModBlocks.COBALT_BLOCK.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATE_MODE_TABS.register(eventBus);
    }
}
