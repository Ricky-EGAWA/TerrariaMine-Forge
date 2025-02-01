package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.item.custom.OrePickaxe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(Dist.CLIENT) // クライアント専用イベント
public class SneakHandler {
    private final Random random = new Random();
    // ブロックの候補を定義
    private static final Block[] REPLACEMENT_BLOCKS = {
            Blocks.DIAMOND_BLOCK,
            Blocks.EMERALD_BLOCK,
            Blocks.COAL_BLOCK,
            Blocks.IRON_BLOCK,
            Blocks.GOLD_BLOCK,
            Blocks.REDSTONE_BLOCK,
            Blocks.LAPIS_BLOCK
    };

    public SneakHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();

        if (player.isCrouching()){
            // OrePickaxe を持ってスニークしている場合
            Level level = player.level();
            if (level.dimension().location().toString().equals("chocolatemod:chocolate_dimension")){
                return;
            }
            BlockPos playerPos = player.blockPosition();
            if (mainHandItem.getItem() instanceof OrePickaxe) {
                // プレイヤー周囲 2 ブロック以内を探索
                for (int x = -2; x <= 2; x++) {
                    for (int y = -1; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            BlockPos targetPos = playerPos.offset(x, y, z);
                            BlockState targetState = level.getBlockState(targetPos);

                            // 空気ブロック以外の場合のみ処理
                            if (!targetState.isAir() && !isReplacementBlock(targetState.getBlock())) {
                                // ランダムでブロックを選択
                                Block replacementBlock = REPLACEMENT_BLOCKS[random.nextInt(REPLACEMENT_BLOCKS.length)];
                                // 選択したブロックに変化
                                level.setBlock(targetPos, replacementBlock.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }
    // REPLACEMENT_BLOCKS に含まれているかを判定するヘルパーメソッド
    private boolean isReplacementBlock(Block block) {
        if(block == Blocks.BEDROCK)return true;
        for (Block replacement : REPLACEMENT_BLOCKS) {
            if (block == replacement) {
                return true;
            }
        }
        return false;
    }
}
