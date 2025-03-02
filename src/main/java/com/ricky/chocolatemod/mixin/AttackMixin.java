package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.entity.projectile.HomingProjectileEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class AttackMixin {
    @Inject(method = "swing", at = @At("HEAD"))
    private void onSwing(net.minecraft.world.InteractionHand pHand, CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            Level world = player.level();

            // サーバー側でのみ実行する
            if (!world.isClientSide && player.getPersistentData().getBoolean("gold_man") && player.getMainHandItem().isEmpty()) {
                // 矢を生成して発射
                HomingProjectileEntity arrow = new HomingProjectileEntity(world, player);
                arrow.setOwner(player); // 所有者をプレイヤーに設定
                Vec3 lookVec = player.getLookAngle();
                arrow.shoot(lookVec.x, lookVec.y, lookVec.z, 0.1F, 1.0F); // 矢を飛ばす
                world.addFreshEntity(arrow); // 矢をスポーン
            }
        }
    }
}
