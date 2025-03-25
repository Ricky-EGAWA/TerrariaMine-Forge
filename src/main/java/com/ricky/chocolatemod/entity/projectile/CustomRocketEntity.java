package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class CustomRocketEntity extends AbstractHurtingProjectile {
    private int explosionRadius = 2;

    public CustomRocketEntity(EntityType<? extends CustomRocketEntity> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public CustomRocketEntity(Level level, Player player) {
        this(ModEntities.CUSTOM_ROCKET.get(), level); // EntityType の取得
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY(), player.getZ()); // 生成位置
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            Vec3 motion = this.getDeltaMovement();
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), motion.x, motion.y, motion.z);
        }
        if (this.tickCount > 30) { // 1.5秒後に爆発
            this.explode();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos impactPos = pResult.getBlockPos();
        Level level = this.level();
        if (!this.level().isClientSide()) {
//            effect(impactPos);
            // 半径5マス内のブロックを変換
            for (int x = -explosionRadius; x <= explosionRadius; x++) {
                for (int y = -explosionRadius; y <= explosionRadius; y++) {
                    for (int z = -explosionRadius; z <= explosionRadius; z++) {
                        BlockPos nearbyPos = impactPos.offset(x, y, z);
                        ChangeChocolate.change(level, nearbyPos, true);
                    }
                }
            }
            // 半径5マス内のエンティティにダメージを与える
            level.getEntities(this, this.getBoundingBox().inflate(explosionRadius), entity -> !(entity instanceof Player))
                    .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 24.0F)); // ハート12個分のダメージ
        }
        this.discard();
    }

    private void explode() {
        BlockPos impactPos = this.getOnPos();
        Level level = this.level();
        if (!this.level().isClientSide()) {
//            effect(impactPos);
            // 半径5マス内のブロックを変換
            for (int x = -explosionRadius; x <= explosionRadius; x++) {
                for (int y = -explosionRadius; y <= explosionRadius; y++) {
                    for (int z = -explosionRadius; z <= explosionRadius; z++) {
                        BlockPos nearbyPos = impactPos.offset(x, y, z);
                        ChangeChocolate.change(level, nearbyPos, true);
                    }
                }
            }
            // 半径5マス内のエンティティにダメージを与える
            level.getEntities(this, this.getBoundingBox().inflate(explosionRadius), entity -> !(entity instanceof Player))
                    .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 24.0F)); // ハート12個分のダメージ
        }
        this.discard();
    }
}
