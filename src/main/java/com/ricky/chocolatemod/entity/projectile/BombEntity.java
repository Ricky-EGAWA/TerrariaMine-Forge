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

public class BombEntity extends ThrowableItemProjectile {
    public BombEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BombEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.BOMB.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos impactPos = pResult.getBlockPos();
        Level level = this.level();
        int explosionRadius = 5;
        if (!this.level().isClientSide()) {



            // 半径5マス内のブロックを変換
            for (int x = -explosionRadius; x <= explosionRadius; x++) {
                for (int y = -explosionRadius; y <= explosionRadius; y++) {
                    for (int z = -explosionRadius; z <= explosionRadius; z++) {
                        BlockPos nearbyPos = impactPos.offset(x, y, z);
                        ChangeChocolate.change(level, nearbyPos, false);
                    }
                }
            }
            // 半径5マス内のエンティティにダメージを与える
            level.getEntities(this, this.getBoundingBox().inflate(explosionRadius), entity -> !(entity instanceof Player))
                    .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 24.0F)); // ハート12個分のダメージ
        }

        if (this.level().isClientSide()) {
            System.out.println("bomb explode");
            // パーティクルの表示処理
            int particleCount = Math.min(explosionRadius * 20, 200); // パーティクルの量を調整
            for (int i = 0; i < particleCount; i++) {
                double offsetX = (Math.random() - 0.5) * explosionRadius * 2;
                double offsetY = (Math.random() - 0.5) * explosionRadius * 2;
                double offsetZ = (Math.random() - 0.5) * explosionRadius * 2;
                level.addParticle(ParticleTypes.HEART,
                        impactPos.getX() + offsetX,
                        impactPos.getY() + offsetY,
                        impactPos.getZ() + offsetZ,
                        0, 0, 0);
            }
        }

        // **爆発音を再生**
        level.playLocalSound(
                impactPos.getX() + 0.5, // サウンドのX座標
                impactPos.getY() + 0.5, // サウンドのY座標
                impactPos.getZ() + 0.5, // サウンドのZ座標
                net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, // 爆発音
                net.minecraft.sounds.SoundSource.BLOCKS, // サウンドソース
                2.0F, // 音量
                1.0F, // ピッチ
                false // 距離減衰の適用
        );
        this.discard();
    }
}