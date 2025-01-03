package com.ricky.xp_giant_mod.mixin;

import com.ricky.xp_giant_mod.ScaleManager;
import net.minecraft.client.Camera;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    // Cameraクラス内のフィールドを@Shadowで参照
    @Shadow
    private boolean initialized;

    @Shadow
    private BlockGetter level;

    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    private float yRot;

    @Shadow
    private float xRot;

    @Shadow
    private float eyeHeightOld;

    @Shadow
    private float eyeHeight;

    @Shadow
    protected abstract void setRotation(float yRot, float xRot);

    @Shadow
    protected abstract void setPosition(double x, double y, double z);

    @Shadow
    protected abstract void move(double x, double y, double z);

    @Shadow
    protected abstract double getMaxZoom(double defaultZoom);
    @Inject(method = "setup", at = @At("TAIL"), cancellable = true)
    public void setupScale(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci) {
        this.initialized = true;
        this.level = pLevel;
        this.entity = pEntity;
        this.detached = pDetached;
        this.setRotation(pEntity.getViewYRot(pPartialTick), pEntity.getViewXRot(pPartialTick));
        this.setPosition(Mth.lerp(pPartialTick, pEntity.xo, pEntity.getX()), Mth.lerp(pPartialTick, pEntity.yo, pEntity.getY()) + (double)Mth.lerp(pPartialTick, this.eyeHeightOld, this.eyeHeight), Mth.lerp(pPartialTick, pEntity.zo, pEntity.getZ()));
        if (pDetached) {
            if (pThirdPersonReverse) {
                this.setRotation(this.yRot + 180.0F, -this.xRot);
            }
            this.move(-this.getMaxZoom(4.0D + 0.5*ScaleManager.getScaleForPlayer((Player) pEntity)), 0.0D, 0.0D);
        } else if (pEntity instanceof LivingEntity && ((LivingEntity)pEntity).isSleeping()) {
            Direction direction = ((LivingEntity)pEntity).getBedOrientation();
            this.setRotation(direction != null ? direction.toYRot() - 180.0F : 0.0F, 0.0F);
            this.move(0.0D, 0.3D, 0.0D);
        }

    }
}
