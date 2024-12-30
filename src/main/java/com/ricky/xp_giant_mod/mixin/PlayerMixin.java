package com.ricky.xp_giant_mod.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        System.out.println("Mixin Injected!");
    }

    // EyeHeightを変更するMixin例
    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    public void modifyEyeHeight(Pose pPose, EntityDimensions pSize, CallbackInfoReturnable<Float> cir) {
        // EyeHeightを設定 (例: 目の高さを1.5Fに変更)
        cir.setReturnValue(4.5F);
    }
}