package com.ricky.xp_giant_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.entity.custom.GiantVindicator;
import com.ricky.xp_giant_mod.event.ChargeJumpHandler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.world.entity.monster.Vindicator;

public class GiantVindicatorRenderer extends VindicatorRenderer {
    public GiantVindicatorRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(Vindicator entity, PoseStack poseStack, float partialTicks) {
        if (entity instanceof GiantVindicator) {
            float scaleFactor = 6;
            if (ChargeJumpHandler.isMobFlattened(entity)){
                poseStack.scale(scaleFactor, scaleFactor/8, scaleFactor);
            }else{
                poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
            }
        }
        super.scale(entity, poseStack, partialTicks);
    }
}
