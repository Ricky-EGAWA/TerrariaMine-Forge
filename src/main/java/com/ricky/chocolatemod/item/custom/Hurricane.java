package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.projectile.HurricaneEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Hurricane extends Item {
    public Hurricane(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // 再生音を鳴らす
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!pLevel.isClientSide) {
            // ハリケーンエンティティを作成
            HurricaneEntity hurricane = new HurricaneEntity(ModEntities.HURRICANE.get(), pLevel);

            // プレイヤーの位置にエンティティを配置
            Vec3 lookVector = pPlayer.getLookAngle(); // プレイヤーの視線方向
            hurricane.setPos(pPlayer.getX() + lookVector.x, pPlayer.getEyeY() - 0.1, pPlayer.getZ() + lookVector.z);

            // 発射方向を設定してエンティティを発射
            hurricane.shootInDirection(lookVector, 1F); // 向きと速度を設定

            // エンティティをワールドに追加
            pLevel.addFreshEntity(hurricane);
        }

        // 使用統計を記録
        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        // アイテムの使用結果を返す
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
