package com.ricky.chocolatemod.block.entity;

import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExchangeStartBlockEntity extends BlockEntity {
    public ExchangeStartBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.EXCHANGE_START_BLOCK_ENTITY.get(), pos, state);
    }
}
