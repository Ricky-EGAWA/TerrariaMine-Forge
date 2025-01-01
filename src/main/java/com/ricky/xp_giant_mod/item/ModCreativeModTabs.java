package com.ricky.xp_giant_mod.item;

import com.ricky.xp_giant_mod.XPGiantMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, XPGiantMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_ITEM_TAB = CREATE_MODE_TABS.register("mod_item_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.XP.get()))
                    .title(Component.translatable("creativetab.mod_item_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.XP.get());
                        pOutput.accept(ModItems.SMALL_XP.get());
                        pOutput.accept(ModItems.MEDIUM_XP.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATE_MODE_TABS.register(eventBus);
    }
}
