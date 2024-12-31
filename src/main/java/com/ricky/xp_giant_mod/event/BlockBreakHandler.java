package com.ricky.xp_giant_mod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        System.out.println("break");
        // ブロックを破壊したプレイヤーとツルハシが ExplodePickaxe か確認
        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.getItem() instanceof PickaxeItem) {
            System.out.println("pickaxe");
            // 破壊したブロックの位置とレベルを取得
            Level level = event.getPlayer().level();
            BlockPos pos = event.getPos();
            breakBlocks(level, pos, player);
        }else if ( itemStack.isEmpty() ){
            Level level = event.getPlayer().level();
            BlockPos pos = event.getPos();
            breakBlocksHand(level, pos, player);
        }
    }
    public static void breakBlocks(Level level, BlockPos pos, Player player) {
        int experienceLevel = player.experienceLevel; // プレイヤーの経験値レベルを取得

        // スケールを経験値レベルに応じて設定
        int r;
        if (experienceLevel < 20) {
            r = 0;
        } else if (experienceLevel < 30) {
            r = 1;
        } else if (experienceLevel < 40) {
            r = 2;
        } else if (experienceLevel < 60) {
            r = 4;
        } else {
            r = 6;
        }
        // 破壊したブロックの周囲2ブロックを掘る
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos offsetPos = pos.offset(x, y, z);
                    if (!offsetPos.equals(pos)) { // 自身のブロックはスキップ
                        BlockState state = level.getBlockState(offsetPos);
                        // 周囲のブロックが破壊可能なものであれば、破壊処理を行う
                        if (!state.isAir() && state.getBlock() != Blocks.BEDROCK) {
                            level.destroyBlock(offsetPos, true, player);
                        }
                    }
                }
            }
        }
    }
    public static void breakBlocksHand(Level level, BlockPos pos, Player player) {
        int experienceLevel = player.experienceLevel; // プレイヤーの経験値レベルを取得

        // スケールを経験値レベルに応じて設定
        int r = 25;
        // 破壊したブロックの周囲2ブロックを掘る
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos offsetPos = pos.offset(x, y, z);
                    if (!offsetPos.equals(pos)) { // 自身のブロックはスキップ
                        BlockState state = level.getBlockState(offsetPos);
                        // 周囲のブロックが破壊可能なものであれば、破壊処理を行う
                        if (!state.isAir() && state.getBlock() != Blocks.BEDROCK) {
                            Block block = state.getBlock();
                            if (block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.DIAMOND_ORE ||
                                    block == Blocks.COAL_ORE || block == Blocks.LAPIS_ORE || block == Blocks.EMERALD_ORE ||
                                    block == Blocks.REDSTONE_ORE || block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.ANCIENT_DEBRIS
                                    ||block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_GOLD_ORE ||
                                    block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_COAL_ORE ||
                                    block == Blocks.DEEPSLATE_LAPIS_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE ||
                                    block == Blocks.DEEPSLATE_REDSTONE_ORE) {
                                level.destroyBlock(offsetPos, true, player);
                            }else{
                                level.destroyBlock(offsetPos, false, player);
                            }
                        }
                    }
                }
            }
        }
    }
}
