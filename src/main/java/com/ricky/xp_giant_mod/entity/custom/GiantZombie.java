package com.ricky.xp_giant_mod.entity.custom;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class GiantZombie extends Zombie {
    public GiantZombie(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public float getScale() {
        // 例: 通常の25倍のサイズ
        return 25;
    }
    @Override
    protected boolean isSunBurnTick() {
        // 日光によるダメージを無効化
        return false;
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
                .add(Attributes.ATTACK_DAMAGE,9)
                .add(Attributes.FOLLOW_RANGE, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2)
                .add(Attributes.MAX_HEALTH, 120.0D); // 体力を120に設定
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
