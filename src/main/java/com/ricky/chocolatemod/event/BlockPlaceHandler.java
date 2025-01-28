package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID)
public class BlockPlaceHandler {

    // 各プレイヤーのブロック設置状況を追跡
    private static final Map<ServerPlayer, Set<Block>> playerBlockProgress = new HashMap<>();


    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        // 設置したエンティティがプレイヤーか確認
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        BlockState placedBlockState = event.getPlacedBlock();
        Block placedBlock = placedBlockState.getBlock();

        // ベースブロック（模様入り磨かれたクォーツブロック）の上に置かれたか確認
        BlockPos belowPos = event.getPos().below();
        BlockState belowBlockState = player.level().getBlockState(belowPos);
        if (!belowBlockState.is(Blocks.CHISELED_QUARTZ_BLOCK)) {
            return; // ベースブロックの上でなければ無視
        }

        // 対象ブロックか確認
        if (isTargetBlock(placedBlock)) {
            // プレイヤーの設置状況を取得または初期化
            playerBlockProgress.putIfAbsent(player, new HashSet<>());
            Set<Block> placedBlocks = playerBlockProgress.get(player);

            // ブロックを記録
            if (placedBlocks.add(placedBlock)) {
                System.out.println("Player " + player.getName().getString() + " placed: " + placedBlock);
            }

            // すべてのブロックが置かれた場合に処理を実行
            if (placedBlocks.contains(ModBlocks.DIAMOND_CHOCOLATE_BLOCK.get())
                    && placedBlocks.contains(ModBlocks.EMERALD_CHOCOLATE_BLOCK.get())
                    && placedBlocks.contains(ModBlocks.IRON_CHOCOLATE_BLOCK.get())
                    && placedBlocks.contains(ModBlocks.LAPIS_CHOCOLATE_BLOCK.get())
                    && placedBlocks.contains(ModBlocks.GOLD_CHOCOLATE_BLOCK.get())) {
                teleportToBossRoom(player);// ボス部屋にテレポート
                placedBlocks.clear(); // 状態をリセット
            }
        }
    }

    private static boolean isTargetBlock(Block block) {
        return block == ModBlocks.DIAMOND_CHOCOLATE_BLOCK.get()
                || block == ModBlocks.IRON_CHOCOLATE_BLOCK.get()
                || block == ModBlocks.GOLD_CHOCOLATE_BLOCK.get()
                || block == ModBlocks.LAPIS_CHOCOLATE_BLOCK.get()
                || block == ModBlocks.EMERALD_CHOCOLATE_BLOCK.get();
    }
    private static void teleportToBossRoom(ServerPlayer serverPlayer){
        // ボス部屋にテレポート
        ResourceKey<Level> customDimensionKey = ResourceKey.create(
                Registries.DIMENSION, new ResourceLocation(ChocolateMod.MOD_ID, "chocolate_dimension")
        );
        ServerLevel chocolateLevel = serverPlayer.getServer().getLevel(customDimensionKey);
        if (chocolateLevel != null) {
            serverPlayer.teleportTo(chocolateLevel, 202.5, 101, 28.5, serverPlayer.getYRot(), serverPlayer.getXRot());
        }
    }
}

