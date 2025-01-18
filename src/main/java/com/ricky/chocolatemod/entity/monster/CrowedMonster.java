package com.ricky.chocolatemod.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CrowedMonster extends Zombie {
    public CrowedMonster(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    protected boolean isSunBurnTick() {
        // 日光によるダメージを無効化
        return false;
    }
    @Override
    public void tick() {
        super.tick();

        // 足元のブロックを取得
        BlockPos blockPosBelow = this.blockPosition().below();
        BlockState blockStateBelow = this.level().getBlockState(blockPosBelow);

        // ブロックが空気または液体でない場合に石炭ブロックに変更
        if (!blockStateBelow.isAir() && !blockStateBelow.liquid() && blockStateBelow.getBlock() != Blocks.BEDROCK) {
            this.level().setBlockAndUpdate(blockPosBelow, Blocks.SCULK.defaultBlockState());
        }
    }
}
