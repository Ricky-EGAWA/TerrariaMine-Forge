package com.ricky.chocolatemod.entity.monster.fighter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class FighterRenderer extends PiglinRenderer {
    // Piglin Brute のバニラテクスチャを使用
    private static final ResourceLocation TEXTURES = new ResourceLocation("minecraft", "textures/entity/piglin/piglin_brute.png");

    public FighterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, ModelLayers.PIGLIN_BRUTE, ModelLayers.PIGLIN_BRUTE_INNER_ARMOR, ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR, false);
    }

    // スケーリング (エンティティを2倍のサイズに)
    @Override
    protected void scale(Mob entity, PoseStack poseStack, float partialTicks) {
        if (entity instanceof FighterEntity) {
            float scaleFactor = 2.0f; // 2倍のスケール
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob pEntity) {
        ResourceLocation resourcelocation = TEXTURES;
        if (resourcelocation == null) {
            throw new IllegalArgumentException("I don't know what texture to use for " + pEntity.getType());
        } else {
            return resourcelocation;
        }
    }
}
