package com.ricky.chocolatemod.block.entity;

import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExchangeCupidBlockEntity extends BlockEntity {
    public ExchangeCupidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.EXCHANGE_CUPID_BLOCK_ENTITY.get(), pos, state);
    }
}
