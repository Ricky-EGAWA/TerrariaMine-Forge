package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
    public void tick() {//TODO ハリケーンが動かない　見た目を変更
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

        // 重力を適用
        if (!this.level().isClientSide) { // サーバーサイドのみで処理
            // Y軸に重力を追加（速度のY成分に重力を加算）
            this.velocity = this.velocity.add(0, -1, 0); // 重力加算 (調整可能)

            // 現在の速度に基づいて移動
            this.setDeltaMovement(this.velocity);

            // 寿命を減らす
            this.lifeTime--;

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
}
