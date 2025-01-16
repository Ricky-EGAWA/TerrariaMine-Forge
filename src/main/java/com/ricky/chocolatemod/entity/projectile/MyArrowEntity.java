package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class MyArrowEntity extends AbstractArrow {
    public MyArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    public MyArrowEntity(Level pLevel, LivingEntity livingEntity){
        super(ModEntities.MY_ARROW.get(), livingEntity, pLevel);
    }

    @Override
    protected void tickDespawn() {
        // 独自の消滅条件を設定することもできます
        super.tickDespawn();
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    // カスタム処理を追加するメソッドもオーバーライドできます
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos blockPos = pResult.getBlockPos();

        // 範囲内のすべてのブロックに対して処理を行う
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos nearbyPos = blockPos.offset(x, y, z);
                    ChangeChocolate.change(this.level(), nearbyPos, false);
                }
            }
        }
        this.discard();
    }
}