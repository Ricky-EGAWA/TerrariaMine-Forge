package com.ricky.xp_giant_mod;

import com.mojang.logging.LogUtils;
import com.ricky.xp_giant_mod.entity.ModEntities;
import com.ricky.xp_giant_mod.entity.client.GiantMummyRenderer;
import com.ricky.xp_giant_mod.entity.client.GiantRavagerRenderer;
import com.ricky.xp_giant_mod.entity.client.GiantVindicatorRenderer;
import com.ricky.xp_giant_mod.entity.client.GiantZombieRenderer;
import com.ricky.xp_giant_mod.event.BlockBreakHandler;
import com.ricky.xp_giant_mod.item.ModCreativeModTabs;
import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
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

        ModEntities.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    //クリエイティブタブに追加
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.GIANT_RAVAGER_SPAWN_EGG);
            event.accept(ModItems.GIANT_ZOMBIE_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.GIANT_ZOMBIE.get(), GiantZombieRenderer::new);
            EntityRenderers.register(ModEntities.GIANT_VINDICATOR.get(), GiantVindicatorRenderer::new);
            EntityRenderers.register(ModEntities.GIANT_RAVAGER.get(), GiantRavagerRenderer::new);
            EntityRenderers.register(ModEntities.GIANT_MUMMY.get(), GiantMummyRenderer::new);
        }
    }
}
