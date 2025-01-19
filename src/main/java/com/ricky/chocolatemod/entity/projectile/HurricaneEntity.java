package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HurricaneEntity extends Entity {
    private int lifeTime; // 寿命を管理するカウンタ
    private Vec3 velocity; // 現在の速度

    public HurricaneEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.lifeTime = 200; // 10秒 (1秒 = 20ティック)
    }

    @Override
    protected void defineSynchedData() {
        // 必要な場合、ここで同期データを定義
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        // エンティティの寿命を読み込む
        if (pCompound.contains("LifeTime")) {
            this.lifeTime = pCompound.getInt("LifeTime");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        // エンティティの寿命を保存
        pCompound.putInt("LifeTime", this.lifeTime);
    }

    @Override
    public void tick() {//TODO パーティクルの変更
        super.tick();
        BlockPos blockPos = this.getOnPos();
        Level level = this.level();
        // ブロックを変換
        for (int x = -8; x <= 8; x++) {
            for (int y = 0; y <= 19; y++) {
                for (int z = -8; z <= 8; z++) {
                    BlockPos nearbyPos = blockPos.offset(x, y, z);
                    ChangeChocolate.change(level, nearbyPos, false);
                }
            }
        }
        if (this.level().isClientSide) {
            spawnParticles();
        }

        // 重力を適用
        if (!this.level().isClientSide) { // サーバーサイドのみで処理
            // 現在の速度に基づいて移動
            this.setDeltaMovement(this.velocity);
            // Y軸に重力を追加（速度のY成分に重力を加算）
            this.velocity = this.velocity.add(0, -1, 0); // 重力加算 (調整可能)

            // 実際に移動
            this.move(MoverType.SELF ,this.getDeltaMovement()); // 自分の速度に基づいて移動

            // 寿命を減らす
            this.lifeTime--;

            // プレイヤー以外のエンティティを検出して上方向に飛ばす
            level.getEntities(this, this.getBoundingBox().inflate(8, 8, 8),
                            entity -> !(entity instanceof Player) && (entity instanceof LivingEntity)) // nullチェック追加
                    .forEach(entity -> {
                        if (entity.isAlive()) { // エンティティが生存しているか確認
                            Vec3 upwardVelocity = new Vec3(0, 1.0, 0);
                            entity.setDeltaMovement(upwardVelocity);
                        }
                    });

            // 寿命が尽きたらエンティティを削除
            if (this.lifeTime <= 0) {
                this.discard(); // エンティティを消す
            }
        }
    }
    public void shootInDirection(Vec3 direction, float speed) {
        // プレイヤーの向きに基づいて進行方向を設定
        this.velocity = direction.normalize().scale(speed); // 速度を設定
    }
    private void spawnParticles() {
        // エンティティの中心座標
        double centerX = this.getX();
        double centerY = this.getY();
        double centerZ = this.getZ();

        // 渦の高さ
        for (int height = 0; height <= 10; height++) {
            // 現在の高さに応じた半径 (上に行くほど広がる)
            double radius = 0.5 + (height * 0.2); // 半径は0.5から始まり、段々広がる
            // 渦の回転速度
            double angleOffset = (this.tickCount + height * 10) * 0.1; // 時間経過で回転

            for (int i = 0; i < 10; i++) { // 10個のパーティクルを生成
                // 円周上の角度 (2πを10等分)
                double angle = angleOffset + (Math.PI * 2 * i / 10);

                // X,Z座標を計算 (円状に配置)
                double particleX = centerX + Math.cos(angle) * radius;
                double particleZ = centerZ + Math.sin(angle) * radius;
                double particleY = centerY + height * 0.5; // 高さに応じてY座標を変更
                System.out.println("pos: "+particleX+" "+particleY+" "+particleZ);

                // パーティクルを生成
                this.level().addParticle(
                        ParticleTypes.SNOWFLAKE, // 使用するパーティクルの種類
                        particleX, particleY, particleZ, // パーティクルの座標
                        0, 0, 0 // パーティクルの速度 (静止)
                );
            }
        }
    }
}
