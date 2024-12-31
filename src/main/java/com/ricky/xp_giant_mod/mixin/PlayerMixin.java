package com.ricky.xp_giant_mod.mixin;

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
        int experienceLevel = player.experienceLevel;

        // スケールを経験値レベルに応じて設定
        float scale;//TODO scale
        if (experienceLevel <= 5) {
            scale = 1.0f;
        } else if (experienceLevel <= 10) {
            scale = 2.5f;
        } else if (experienceLevel <= 20) {
            scale = 5.0f;
        } else {
            scale = 7.5f; // レベル20を超える場合は任意で設定（例: 7.5）
        }
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
}