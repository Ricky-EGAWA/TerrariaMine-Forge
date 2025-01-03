package com.ricky.xp_giant_mod;

import com.mojang.logging.LogUtils;
import com.ricky.xp_giant_mod.effect.ModEffects;
import com.ricky.xp_giant_mod.event.BlockBreakHandler;
import com.ricky.xp_giant_mod.item.ModCreativeModTabs;
import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(XPGiantMod.MOD_ID)
public class XPGiantMod {
    public static final String MOD_ID = "xp_giant_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public XPGiantMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // アイテムやタブの登録
        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);

        // 共通セットアップイベントの登録
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        // このMODのクラス自体をイベントバスに登録
        MinecraftForge.EVENT_BUS.register(this);

        // BlockBreakHandlerの登録
        MinecraftForge.EVENT_BUS.register(BlockBreakHandler.class);

//        ModEffects.EFFECTS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    //クリエイティブタブに追加
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            
        }
    }
}
