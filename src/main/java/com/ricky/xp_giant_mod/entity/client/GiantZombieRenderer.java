package com.ricky.xp_giant_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.entity.custom.GiantZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.monster.Zombie;

public class GiantZombieRenderer extends ZombieRenderer {
    public GiantZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(Zombie entity, PoseStack poseStack, float partialTicks) {
        if (entity instanceof GiantZombie) {
            float scaleFactor = 25; // 25倍のスケール
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }
        super.scale(entity, poseStack, partialTicks);
    }
}
