package com.ricky.xp_giant_mod.entity.custom;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class GiantRavager extends Ravager {
    public GiantRavager(EntityType<? extends Ravager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public float getScale() {
        return 12.5f;
    }
    @Override
    public boolean isPersistenceRequired() {
        // デスポーンを防止
        return true;
    }
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        // 目線の高さを調整（身長に合わせた値を設定）
        return 1.8F * this.getScale(); // スケールに基づく目線の高さ
    }
    public static AttributeSupplier.Builder createAttributes() {
        // GiantZombieの特別な属性を設定
        return Zombie.createAttributes()
                .add(Attributes.ATTACK_DAMAGE,4)
                .add(Attributes.FOLLOW_RANGE, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 20);
    }
    @Override
    protected void dropCustomDeathLoot(net.minecraft.world.damagesource.DamageSource damageSource, int lootingLevel, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, lootingLevel, recentlyHit); // 既存のドロップを保持する場合に呼び出す

        // カスタムドロップを追加
        if (!this.level().isClientSide) {
            // ドロップするアイテムの例: エメラルド
            this.spawnAtLocation(ModItems.XP.get(), 1); // 数を変更したい場合は第二引数を調整
        }
    }
}
