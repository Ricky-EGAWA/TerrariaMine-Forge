package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class HomingProjectileEntity extends Projectile {
    private int lifeTime = 500;
    private LivingEntity target;

    public HomingProjectileEntity(EntityType<? extends HomingProjectileEntity> entityType, Level world) {
        super(entityType, world);
    }

    public HomingProjectileEntity(Level world, LivingEntity shooter) {
        this(ModEntities.HOMING_PROJECTILE.get(), world);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.findTarget();
    }

    private void findTarget() {
        List<Mob> mobs = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(16));
        if (!mobs.isEmpty()) {
            this.target = mobs.get(0); // 最も近いモブをターゲットにする
        }
    }

    @Override
    public void tick() {
        super.tick();
        lifeTime--;
        if(lifeTime<0){
            this.discard();
        }

        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }

        if (this.target != null && this.target.isAlive()) {
            Vec3 direction = new Vec3(this.target.getX() - this.getX(), this.target.getY() + 0.5 - this.getY(), this.target.getZ() - this.getZ());
            direction = direction.normalize().scale(0.3);
            this.setDeltaMovement(direction);
        }

        // 衝突判定（手動で行う）
        Vec3 nextPos = this.position().add(this.getDeltaMovement());
        BlockHitResult blockHitResult = this.level().clip(new net.minecraft.world.level.ClipContext(this.position(), nextPos, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, this));

        if (blockHitResult.getType() != BlockHitResult.Type.MISS) {
            this.onHit(blockHitResult);
        }

        EntityHitResult entityHitResult = this.findEntityHitResult();
        if (entityHitResult != null) {
            this.onHitEntity(entityHitResult);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    protected void defineSynchedData() {
        this.setBoundingBox(this.getBoundingBox().inflate(0.3)); // ヒットボックスを大きくする
    }



    @Override
    protected void onHitEntity(EntityHitResult pResult){
        System.out.println("hit entity");
        Level level = this.level();
        if(level.isClientSide){
            return;
        }
        level.getEntities(this, this.getBoundingBox().inflate(3), entity -> !(entity instanceof Player))
                .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 60));
        this.discard();
    }
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        System.out.println("hit block");
        this.discard();
    }


    // `EntityHitResult` を取得するメソッドを追加
    private EntityHitResult findEntityHitResult() {
        Vec3 start = this.position();
        Vec3 end = start.add(this.getDeltaMovement());
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0));

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : list) {
            if (entity == this.getOwner()) continue; // 発射者は無視

            AABB aabb = entity.getBoundingBox();
            Optional<Vec3> optional = aabb.clip(start, end);
            if (optional.isPresent()) {
                double distance = start.distanceTo(optional.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        return closestEntity != null ? new EntityHitResult(closestEntity) : null;
    }
}
