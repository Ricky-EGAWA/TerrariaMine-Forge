package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.ModBlocks;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerWalkEvent {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // サーバー側のみ処理
        if (!event.player.level().isClientSide && event.phase == TickEvent.Phase.END) {
            // プレイヤーの足元の位置を取得
            BlockPos position = event.player.blockPosition().below();

            ChangeChocolate.change(event.player.level(), position, false);
        }
    }

    // イベント登録
    public static void register() {
        MinecraftForge.EVENT_BUS.register(PlayerWalkEvent.class);
    }
}
