package com.ricky.xp_giant_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.entity.custom.GiantMummy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class GiantMummyRenderer extends ZombieRenderer {
    private static final ResourceLocation GIANT_MUMMY_TEXTURE = new ResourceLocation(XPGiantMod.MOD_ID, "textures/entity/giant_mummy.png");

    public GiantMummyRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(Zombie entity, PoseStack poseStack, float partialTicks) {
        if (entity instanceof GiantMummy) {
            float scaleFactor = 2.5f; // 2.5倍のスケール
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }
        super.scale(entity, poseStack, partialTicks);
    }
    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        if (entity instanceof GiantMummy) {
            return GIANT_MUMMY_TEXTURE;
        }
        return super.getTextureLocation(entity);
    }
}
