package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class MagicEntity extends ThrowableItemProjectile {
    public MagicEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MagicEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.MAGIC.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos impactPos = pResult.getBlockPos();
        Level level = this.level();
        int explosionRadius = 1;
        if (!this.level().isClientSide()) {
            for (int x = -explosionRadius; x <= explosionRadius; x++) {
                for (int y = -explosionRadius; y <= explosionRadius; y++) {
                    for (int z = -explosionRadius; z <= explosionRadius; z++) {
                        BlockPos nearbyPos = impactPos.offset(x, y, z);
                        ChangeChocolate.change(level, nearbyPos, false);
                    }
                }
            }
        }
        this.discard();
    }
}