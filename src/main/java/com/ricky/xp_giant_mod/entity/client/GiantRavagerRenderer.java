package com.ricky.xp_giant_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.entity.custom.GiantRavager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.world.entity.monster.Ravager;

public class GiantRavagerRenderer extends RavagerRenderer {
    public GiantRavagerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(Ravager entity, PoseStack poseStack, float partialTicks) {
        if (entity instanceof GiantRavager) {
            float scaleFactor = 12.5f;
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }
        super.scale(entity, poseStack, partialTicks);
    }
}
