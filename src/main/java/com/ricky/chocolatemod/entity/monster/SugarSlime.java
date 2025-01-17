package com.ricky.chocolatemod.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SugarSlime extends Slime {
    public SugarSlime(EntityType<? extends Slime> entityType, Level world) {
        super(entityType, world);
        this.setSize(2,true);
        this.refreshDimensions();
    }
    @Override
    protected void dropExperience() {
        super.dropExperience();
        // カスタムドロップ: 砂糖をドロップする
        if (!this.level().isClientSide) {
            int dropCount = this.random.nextInt(2) + 1; // ドロップ数: 1～2個
            for (int i = 0; i < dropCount; i++) {
                ItemEntity sugar = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), Items.SUGAR.getDefaultInstance());
                this.level().addFreshEntity(sugar);
            }
        }
    }


    // Slimeの基本的な能力値を設定
    public static AttributeSupplier.Builder createAttributes() {
        return Slime.createLivingAttributes() // Slimeの基本属性を手動で追加
                .add(Attributes.MAX_HEALTH, 16.0D) // HP
                .add(Attributes.FOLLOW_RANGE, 35.0D) // フォロー範囲
                .add(Attributes.MOVEMENT_SPEED, 0.23D) // 移動速度
                .add(Attributes.ATTACK_DAMAGE, 3.0D) // 攻撃力
                .add(Attributes.ARMOR, 2.0D); // 防御力
    }
}
