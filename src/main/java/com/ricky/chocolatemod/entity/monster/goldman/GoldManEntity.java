package com.ricky.chocolatemod.entity.monster.goldman;

import com.ricky.chocolatemod.client.ModParticles;
import com.ricky.chocolatemod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Explosion;
import net.minecraft.core.particles.ParticleTypes; // 追加

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class GoldManEntity extends PathfinderMob {

    private boolean movingToSafeZone = false;
    private BlockPos safeZone = null;
    private int explodeTimer = -1; // -1 の場合はカウントダウンなし

    public GoldManEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SeekAndExplodeGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (explodeTimer > 0) {
            explodeTimer--;
            if (explodeTimer == 0) {
                explode();
            }
        }
    }

    public void explodeWithDelay() {
        if (explodeTimer == -1) {
            explodeTimer = 30; // 1.5秒後に爆発（30ティック）
        }
    }

    public void explode() {
        if (!this.level().isClientSide) {
            Explosion explosion = this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.NONE);
            explosion.clearToBlow(); // プレイヤーにはダメージを与えない
            this.discard(); // エンティティを削除
        }
    }

    /**
     * ビックリマークを頭上に表示
     */
    private void displayExclamationMark() {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            for (int i = 0; i < 5; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * 0.3;
                double offsetY = 2.0 + this.random.nextDouble() * 0.3;
                double offsetZ = (this.random.nextDouble() - 0.5) * 0.3;
                serverLevel.sendParticles(ParticleTypes.LAVA, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0, 0, 0, 0);
            }
        }
    }


    public class SeekAndExplodeGoal extends Goal {
        private final GoldManEntity entity;
        private LivingEntity targetMonster;

        public SeekAndExplodeGoal(GoldManEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (entity.movingToSafeZone) {
                return true;
            }

            // 20ブロック以内のモンスターを探す
            targetMonster = findTargetMonster();

            if (targetMonster != null) {
                entity.displayExclamationMark(); // ここでビックリマークを出す
                return true;
            }

            // モンスターがいない場合、足元のブロックをチェック
            BlockPos pos = entity.blockPosition();
            if (entity.level().getBlockState(pos.below()).is(Blocks.GOLD_BLOCK)) {
                Optional<BlockPos> safePos = findSafeZone();
                if (safePos.isPresent()) {
                    entity.safeZone = safePos.get();
                    entity.movingToSafeZone = true;
                    return true;
                } else {
                    return true; // 安全地帯が見つからなかったら、その場で自爆
                }
            }

            return true; // その場で自爆
        }

        @Override
        public void tick() {
            if (entity.explodeTimer != -1) {
                return; // 爆発カウントダウン中は移動しない
            }

            if (targetMonster != null) {
                entity.getNavigation().moveTo(targetMonster, 1.0D);
                if (entity.distanceTo(targetMonster) < 2.0D) {
                    entity.explodeWithDelay(); // 1.5秒後に爆発
                }
            } else if (entity.movingToSafeZone) {
                entity.getNavigation().moveTo(entity.safeZone.getX(), entity.safeZone.getY(), entity.safeZone.getZ(), 1.0D);
                if (entity.blockPosition().closerThan(entity.safeZone, 1.0D)) {
                    entity.explodeWithDelay(); // 1.5秒後に爆発
                }
            } else {
                entity.explodeWithDelay(); // 1.5秒後に爆発
            }
        }

        /**
         * 20ブロック以内のモンスターを探す
         */
        private LivingEntity findTargetMonster() {
            List<Mob> monsters = entity.level().getEntitiesOfClass(Mob.class, new AABB(entity.blockPosition()).inflate(20),
                    mob -> mob.getType().getCategory() == MobCategory.MONSTER && !(mob instanceof GoldManEntity));

            return monsters.isEmpty() ? null : monsters.get(0);
        }

        /**
         * 金ブロックがない場所を探す
         */
        private Optional<BlockPos> findSafeZone() {
            BlockPos origin = entity.blockPosition();
            for (int dx = -25; dx <= 25; dx++) {
                for (int dz = -25; dz <= 25; dz++) {
                    BlockPos checkPos = origin.offset(dx, 0, dz);
                    if (!entity.level().getBlockState(checkPos.below()).is(Blocks.GOLD_BLOCK)) {
                        return Optional.of(checkPos);
                    }
                }
            }
            return Optional.empty();
        }
    }

    public static void spawnCustomPlayer(ServerLevel level, BlockPos pos) {
        GoldManEntity entity = ModEntities.GOLD_MAN.get().create(level);
        if (entity != null) {
            entity.setPos(pos.getX(), pos.getY(), pos.getZ());

            level.addFreshEntity(entity);
        }
    }
}
