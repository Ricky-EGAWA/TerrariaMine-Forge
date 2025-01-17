package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.entity.projectile.MyArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;

public class Slingshot extends BowItem {
    private static final Properties pProperties = new Properties();

    public Slingshot() {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {

            boolean flag = player.getAbilities().instabuild;
            ItemStack itemstack = ItemStack.EMPTY; // 矢を使わないため

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            // 発射力の計算
            float f = getPowerForTime(i);
            if (!(f < 0.5D)) {
                if (!pLevel.isClientSide) {
                    // MyArrowEntityを作成して発射
                    MyArrowEntity arrowEntity = new MyArrowEntity(pLevel, player);
                    arrowEntity.setOwner(player);  // 発射者を設定
                    arrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);

                    pLevel.addFreshEntity(arrowEntity);  // 発射した弾をワールドに追加
                }

                // 発射音を再生
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}
