package com.ricky.chocolatemod.util;

import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class ChangeChocolate {
    // 適用するバニラブロック（すべての花・草・背の高い草）
    private static final Set<Block> AFFECTED_BLOCKS = Set.of(
            Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET,
            Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY,
            Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY, Blocks.SUNFLOWER, Blocks.LILAC, Blocks.ROSE_BUSH,
            Blocks.PEONY, Blocks.GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN
    );
    public static void change(Level level, BlockPos blockPos, boolean h){
        if (level.dimension().location().toString().equals("chocolatemod:chocolate_dimension")){
            return;
        }
        BlockState targetState = level.getBlockState(blockPos);
        if(h){ //腐敗したブロックを置き換え可能
            if (!targetState.isAir() && targetState.getBlock() != Blocks.BEDROCK && targetState.getBlock() != ModBlocks.CHOCOLATE_BLOCK.get()
                    && targetState.isSolid()){
                //草花を削除
                BlockPos abovePos = blockPos.above();
                BlockState aboveState = level.getBlockState(abovePos);

                if (AFFECTED_BLOCKS.contains(aboveState.getBlock())) {
                    level.setBlockAndUpdate(abovePos, Blocks.AIR.defaultBlockState()); // 花や草を削除
                }
                level.setBlock(blockPos, ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), 3);
                ChocolateCounter.getInstance().addChocolate(3);
            }
        }else {//腐敗したブロックを置き換えられない
            if (!targetState.isAir() && targetState.getBlock() != Blocks.BEDROCK && targetState.getBlock() != Blocks.SCULK && targetState.getBlock() != ModBlocks.CHOCOLATE_BLOCK.get()
                    && targetState.isSolid()){
                //草花を削除
                BlockPos abovePos = blockPos.above();
                BlockState aboveState = level.getBlockState(abovePos);

                if (AFFECTED_BLOCKS.contains(aboveState.getBlock())) {
                    level.setBlockAndUpdate(abovePos, Blocks.AIR.defaultBlockState()); // 花や草を削除
                }
                level.setBlock(blockPos, ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), 3);
                ChocolateCounter.getInstance().addChocolate(1);
            }
        }
    }
}
