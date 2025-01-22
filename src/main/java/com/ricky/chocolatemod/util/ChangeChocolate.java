package com.ricky.chocolatemod.util;

import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ChangeChocolate {
    public static void change(Level level, BlockPos blockPos, boolean h){
        BlockState targetState = level.getBlockState(blockPos);
        if(h){ //腐敗したブロックを置き換え可能
            if (!targetState.isAir() && targetState.getBlock() != Blocks.BEDROCK && targetState.getBlock() != ModBlocks.CHOCOLATE_BLOCK.get()){
                level.setBlock(blockPos, ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), 3);
                ChocolateCounter.getInstance().addChocolate(3);
            }
        }else {//腐敗したブロックを置き換えられない
            if (!targetState.isAir() && targetState.getBlock() != Blocks.BEDROCK && targetState.getBlock() != Blocks.SCULK && targetState.getBlock() != ModBlocks.CHOCOLATE_BLOCK.get()){
                level.setBlock(blockPos, ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), 3);
                ChocolateCounter.getInstance().addChocolate(1);
            }
        }
    }
}
