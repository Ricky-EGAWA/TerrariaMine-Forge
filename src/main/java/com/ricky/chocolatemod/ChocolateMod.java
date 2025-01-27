package com.ricky.chocolatemod;

import com.mojang.logging.LogUtils;
import com.ricky.chocolatemod.block.ModBlocks;
import com.ricky.chocolatemod.block.milk.ModFluidTypes;
import com.ricky.chocolatemod.block.milk.ModFluids;
import com.ricky.chocolatemod.block.renderer.*;
import com.ricky.chocolatemod.client.ChocolateHudOverlay;
import com.ricky.chocolatemod.effect.ModEffects;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedMonsterRenderer;
import com.ricky.chocolatemod.entity.monster.CrowedWitherRenderer;
import com.ricky.chocolatemod.entity.monster.SugarSlimeRenderer;
import com.ricky.chocolatemod.entity.projectile.HurricaneRenderer;
import com.ricky.chocolatemod.entity.projectile.MagicEntityRenderer;
import com.ricky.chocolatemod.entity.projectile.MyArrowRenderer;
import com.ricky.chocolatemod.entity.spawn.SpawnModifier;
import com.ricky.chocolatemod.event.ChocolateSwordEventHandler;
import com.ricky.chocolatemod.event.SneakHandler;
import com.ricky.chocolatemod.item.ModCreativeModTabs;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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

@Mod(ChocolateMod.MOD_ID)
public class ChocolateMod {
    public static final String MOD_ID = "chocolatemod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChocolateMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        // BiomeModifierの登録
        SpawnModifier.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        new SneakHandler();
        // エンティティの登録
        ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(ChocolateSwordEventHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    //クリエイティブタブに追加
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MILK.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MILK.get(), RenderType.solid());

            // クライアント側のセットアップでレンダラーを登録
            EntityRenderers.register(ModEntities.MY_ARROW.get(), MyArrowRenderer::new);
            EntityRenderers.register(ModEntities.BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.SUGAR_SLIME.get(), SugarSlimeRenderer::new);
            EntityRenderers.register(ModEntities.CROWED_MONSTER.get(), CrowedMonsterRenderer::new);
            EntityRenderers.register(ModEntities.CROWED_WITHER.get(), CrowedWitherRenderer::new);
            EntityRenderers.register(ModEntities.HURRICANE.get(), HurricaneRenderer::new);
            EntityRenderers.register(ModEntities.MAGIC.get(), MagicEntityRenderer::new);


            BlockEntityRenderers.register(ModBlocks.EXCHANGE_ORE_PICKAXE_BLOCK_ENTITY.get(), ExchangeOrePickaxeRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_HEALER_BLOCK_ENTITY.get(), ExchangeHealerRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_HURRICANE_BLOCK_ENTITY.get(), ExchangeHurricaneRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_BOMB_BLOCK_ENTITY.get(), ExchangeBombRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_SWORD_BLOCK_ENTITY.get(), ExchangeSwordRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_SLINGSHOT_BLOCK_ENTITY.get(), ExchangeSlingshotRenderer::new);
            BlockEntityRenderers.register(ModBlocks.EXCHANGE_MAGIC_BLOCK_ENTITY.get(), ExchangeMagicRenderer::new);

            new ChocolateHudOverlay(); // HUD オーバーレイの初期化
        }
    }
}
