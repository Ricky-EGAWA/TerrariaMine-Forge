package com.ricky.xp_giant_mod.entity.custom;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class GiantMummy extends Zombie {
    public GiantMummy(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public float getScale() {
        // 例: 通常の2.5倍のサイズ
        return 2.5f;
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
