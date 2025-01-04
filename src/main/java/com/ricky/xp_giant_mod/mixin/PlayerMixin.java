package com.ricky.xp_giant_mod.mixin;

import com.ricky.xp_giant_mod.ScaleManager;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    // EyeHeightを変更するMixin例
    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    public void modifyEyeHeight(Pose pPose, EntityDimensions pSize, CallbackInfoReturnable<Float> cir) {
        // EyeHeightを設定 (例: 目の高さを1.5Fに変更)
        // プレイヤーの経験値レベルを取得
        Player player = (Player) (Object) this;
        // 共通クラスを使用してスケールを取得
        float scale = ScaleManager.getScaleForPlayer(player);
        switch (pPose) {
            case SWIMMING:
            case FALL_FLYING:
            case SPIN_ATTACK:
                cir.setReturnValue(0.4f*scale);
                break;
            case CROUCHING:
                cir.setReturnValue(1.27F*scale);
                break;
            default:
                cir.setReturnValue(1.62F*scale);
                break;
        }
    }
    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    public void modifyHitBox(Pose pPose, CallbackInfoReturnable<EntityDimensions> cir) {
        float scale = ScaleManager.getScaleForPlayer((Player) (Object) this);
        // スケールに応じたヒットボックスを設定
        cir.setReturnValue(EntityDimensions.scalable(0.6F * scale, 1.8F * scale));
        // デフォルトの動作をスキップ
        cir.cancel();
    }
}